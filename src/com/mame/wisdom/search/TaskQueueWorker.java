package com.mame.wisdom.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

public class TaskQueueWorker extends HttpServlet {

	private final static String TAG = TaskQueueWorker.class.getSimpleName();

	private static final Index INDEX = SearchServiceFactory.getSearchService()
			.getIndex(IndexSpec.newBuilder().setName("wisdom_search_db"));

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DbgUtil.showLog(TAG, "doPost");

		// String value = request.getParameter(SearchConstants.KEY);
		String value = getPayload(request);
		DbgUtil.showLog(TAG, "value: " + value);
		// String body = getRequestBody(request);
		// JSONObject json = new JSONObject(body);

		if (value == null) {
			return;
		}

		WDWisdomData data = JsonParseUtil.createWisdomDataFromJson(value);

		// String key = request.getParameter("key");
		// DbgUtil.showLog(TAG, "key: " + key);

		// In this case, we use wisdom Id as key.
		String key = String.valueOf(data.getWisdomId());

		String messages = JsonParseUtil.parseWisdomItemEntitiesToJsonArray(
				data.getItems()).toString();

		Document doc = Document
				.newBuilder()
				.setId(key)
				// Setting the document identifer is optional. If omitted, the
				// search service will create an identifier.
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_TITLE)
								.setText(data.getTitle()))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_DESCRIPTION)
								.setText(data.getDescription()))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_TAG)
								.setText(data.getTag()))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_ITEM)
								.setText(messages)).build();
		try {
			INDEX.put(doc);
		} catch (PutException e) {
			DbgUtil.showLog(TAG, "PutException: " + e.getMessage());
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
					.getCode())) {
				// retry putting the document
			}
		}

	}

	private List<Long> searchWisdomByParameter(String parameter) {
		DbgUtil.showLog(TAG, "searchWisdomByParameter");
		if (parameter == null) {
			return null;
		}

		List<Long> result = new ArrayList<Long>();

		Results<ScoredDocument> documents = INDEX.search(parameter);
		// List<HtmlModel> models = new ArrayList<HtmlModel>();
		for (ScoredDocument document : documents) {
			// HtmlModel model = new HtmlModel();

			long id = Long.valueOf(document.getId());

			// String title = document.getOnlyField(
			// SearchConstants.KEY_SERACH_TITLE).getText();
			DbgUtil.showLog(TAG, "id: " + id);
			result.add(id);

			// model.setFileName(document.getOnlyField("fileName").getText());
			// model.setContent(document.getOnlyField("content").getHTML());
			// models.add(model);
		}

		return result;
	}

	private String getPayload(HttpServletRequest req) throws IOException {
		InputStream is = req.getInputStream();
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

		// ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		//
		// int length;
		// byte[] buffer = new byte[1024];
		//
		// while ((length = inputStream.read(buffer)) >= 0)
		// byteArrayStream.write(buffer, 0, length);
		//
		// if (byteArrayStream.size() > 0)
		// return MAPPER.readValue(byteArrayStream.toByteArray(),
		// PayloadObject.class);
		//
		// return null;
	}

}