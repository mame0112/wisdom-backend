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
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.util.DbgUtil;

public class TaskQueueWorker extends HttpServlet {

	private final static String TAG = TaskQueueWorker.class.getSimpleName();

	private static final Index INDEX = SearchServiceFactory.getSearchService()
			.getIndex(IndexSpec.newBuilder().setName(WConstant.SEARCH_INDEX));

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DbgUtil.showLog(TAG, "doPost");

		String key = request.getParameter(SearchConstants.KEY);
		String title = request.getParameter(SearchConstants.KEY_SERACH_TITLE);
		String description = request
				.getParameter(SearchConstants.KEY_SERACH_DESCRIPTION);
		String tag = request.getParameter(SearchConstants.KEY_SERACH_TAG);
		String category = request
				.getParameter(SearchConstants.KEY_SEARCH_CATEGORY);
		String subCategory = request
				.getParameter(SearchConstants.KEY_SEARCH_SUB_CATEGORY);

		DbgUtil.showLog(TAG, "key: " + key + " title: " + title + " desc: "
				+ description + " tag: " + tag);

		String items = request.getParameter(SearchConstants.KEY_SERACH_ITEM);
		DbgUtil.showLog(TAG, "item: " + items);

		Document doc = Document
				.newBuilder()
				.setId(key)
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_TITLE)
								.setText(title))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_DESCRIPTION)
								.setText(description))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_TAG)
								.setText(tag))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SEARCH_CATEGORY)
								.setText(category))
				.addField(
						Field.newBuilder()
								.setName(
										SearchConstants.KEY_SEARCH_SUB_CATEGORY)
								.setText(subCategory))
				.addField(
						Field.newBuilder()
								.setName(SearchConstants.KEY_SERACH_ITEM)
								.setText(items)).build();
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
