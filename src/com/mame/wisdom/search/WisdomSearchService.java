package com.mame.wisdom.search;

import java.util.Date;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.util.DbgUtil;

public class WisdomSearchService {

	private final static String TAG = WisdomSearchService.class.getSimpleName();

	private static final Index INDEX = SearchServiceFactory.getSearchService()
			.getIndex(IndexSpec.newBuilder().setName("wisdom_search_db"));

	public void storeNewWisdom(WDWisdomData data) {

		DbgUtil.showLog(TAG, "storeNewWisdom");
		if (data == null) {
			return;
		}

		// In this case, we use wisdom Id as key.
		String key = String.valueOf(data.getWisdomId());

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
								.setText("TBU")).build();
		// TODO need parse Item into string

		// IndexSpec indexSpec =
		// IndexSpec.newBuilder().setName(indexName).build();
		// Index index = SearchServiceFactory.getSearchService().getIndex(
		// indexSpec);

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

	// TODO To be updated later
	public void updateWisdom(WDWisdomData data) {
		if (data == null) {
			return;
		}
	}

	public String searchWisdomByParameter(String parameter) {
		DbgUtil.showLog(TAG, "searchWisdomByParameter");
		if (parameter == null) {
			return null;
		}

		Results<ScoredDocument> documents = INDEX.search(parameter);
		// List<HtmlModel> models = new ArrayList<HtmlModel>();
		for (ScoredDocument document : documents) {
			// HtmlModel model = new HtmlModel();
			String title = document.getOnlyField(
					SearchConstants.KEY_SERACH_TITLE).getText();
			DbgUtil.showLog(TAG, "title: " + title);
			// model.setFileName(document.getOnlyField("fileName").getText());
			// model.setContent(document.getOnlyField("content").getHTML());
			// models.add(model);
		}

		// try {
		// String queryString = "product: piano AND price &lt; 5000";
		// Results<ScoredDocument> results = getIndex().search(queryString);
		//
		// // Iterate over the documents in the results
		// for (ScoredDocument document : results) {
		// // handle results
		// }
		// } catch (SearchException e) {
		// if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
		// .getCode())) {
		// // retry
		// }
		// }

		return null;
	}

	// public void createDocument() {
	// Document.Builder builder = Document.newBuilder();
	//
	// Field.Builder fBuilder = Field.newBuilder();
	// fBuilder.setText("test text");
	//
	// builder.addField(fBuilder);
	//
	// // Create Document instance
	// Document doc = builder.build();
	//
	// // Create IndexSpec.Builder to setup setting infor reagrding index.
	// IndexSpec.Builder indexBuilder = IndexSpec.newBuilder();
	// indexBuilder.setName("testIndex");
	//
	// //Create index instance
	// SearchService service = SearchServiceFactory.getSearchService();
	//
	// //get index instance
	// Index index = service.getIndex(indexBuilder);
	//
	// //Add to index
	// try {
	// index.
	// } catch(RuntimeException e){
	//
	// }
	// }

	// public void createDocument() {
	//
	// String myDocId = "PA6-5000";
	//
	// Document doc = Document
	// .newBuilder()
	// .setId(myDocId)
	// // Setting the document identifer is optional. If omitted, the
	// // search service will create an identifier.
	// .addField(
	// Field.newBuilder().setName("content")
	// .setText("the rain in spain"))
	// .addField(
	// Field.newBuilder().setName("email").setText("myEmail"))
	// .addField(
	// Field.newBuilder().setName("domain")
	// .setAtom("myDomain"))
	// .addField(
	// Field.newBuilder().setName("published")
	// .setDate(new Date())).build();
	//
	// String indexName = "indexName";
	//
	// IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
	// Index index = SearchServiceFactory.getSearchService().getIndex(
	// indexSpec);
	//
	// try {
	// index.put(doc);
	// } catch (PutException e) {
	// if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
	// .getCode())) {
	// // retry putting the document
	// }
	// }
	// }
}
