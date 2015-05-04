package com.mame.wisdom.search;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.search.DeleteException;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchQueryException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryKeyData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

public class WisdomSearchService {

	private final static String TAG = WisdomSearchService.class.getSimpleName();

	private static final Index INDEX = SearchServiceFactory.getSearchService()
			.getIndex(IndexSpec.newBuilder().setName(WConstant.SEARCH_INDEX));

	private final static String WORKER = "worker";

	// public void addValue(WDWisdomData data, String category, String
	// subCategory) {
	public void addValue(WDWisdomData data) {
		DbgUtil.showLog(TAG, "addValue");
		if (data == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		JSONArray array = JsonParseUtil.parseWisdomItemEntitiesToJsonArray(data
				.getItems());

		DbgUtil.showLog(TAG, "object is not null");
		// This "process" is a name that is defined in queue.xml
		Queue queue = QueueFactory.getQueue(WORKER);
		TaskOptions to = TaskOptions.Builder
				.withUrl("/" + WORKER)
				.param(SearchConstants.KEY, String.valueOf(data.getWisdomId()))
				.param(SearchConstants.KEY_SERACH_DESCRIPTION,
						String.valueOf(data.getDescription()))
				.param(SearchConstants.KEY_SERACH_TAG, data.getTag())
				// .param(SearchConstants.KEY_SEARCH_CATEGORY, category)
				// .param(SearchConstants.KEY_SEARCH_SUB_CATEGORY, subCategory)
				.param(SearchConstants.KEY_SERACH_ITEM, array.toString())
				.param(SearchConstants.KEY_SERACH_TITLE, data.getTitle());

		queue.add(to.method(Method.POST));

	}

	public List<WDSubCategoryKeyData> searchWisdomByParameter(String parameter) {

		DbgUtil.showLog(TAG, "searchWisdomByParameter");

		List<WDSubCategoryKeyData> response = new ArrayList<WDSubCategoryKeyData>();

		try {
			Query q = Query.newBuilder().build(parameter);
			Results<ScoredDocument> results = INDEX.search(q);
			for (ScoredDocument document : results) {

				String docId = document.getId();
				DbgUtil.showLog(TAG, "docId: " + docId);
				String category = document.getOnlyField(
						SearchConstants.KEY_SEARCH_CATEGORY).getText();
				String subCategory = document.getOnlyField(
						SearchConstants.KEY_SEARCH_SUB_CATEGORY).getText();

				WDSubCategoryKeyData data = new WDSubCategoryKeyData(category,
						subCategory, Long.valueOf(docId));

				response.add(data);
			}
		} catch (SearchException e) {
			DbgUtil.showLog(TAG, "SearchException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			DbgUtil.showLog(TAG, "IllegalArgumentException: " + e.getMessage());
		} catch (SearchQueryException e) {
			DbgUtil.showLog(TAG, "SearchQueryException: " + e.getMessage());
		}

		return response;
	}

	/**
	 * Delete all documents And this is debug method.
	 */
	public void deleteAllSearchDocuments() {
		DbgUtil.showLog(TAG, "getAllDocumentId");

		try {
			Results<ScoredDocument> results = INDEX.search("");
			for (ScoredDocument document : results) {

				String docId = document.getId();
				DbgUtil.showLog(TAG, "docId: " + docId);
				deleteWisdom(docId);
			}
		} catch (SearchException e) {
			DbgUtil.showLog(TAG, "SearchException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			DbgUtil.showLog(TAG, "IllegalArgumentException: " + e.getMessage());
		} catch (SearchQueryException e) {
			DbgUtil.showLog(TAG, "SearchQueryException: " + e.getMessage());
		}

	}

	private void deleteWisdom(String documentId) {

		DbgUtil.showLog(TAG, "deleteWisdom");

		if (documentId == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		try {
			INDEX.delete(documentId);
		} catch (DeleteException e) {
			DbgUtil.showLog(TAG, "DeleteException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			DbgUtil.showLog(TAG, "IllegalArgumentException: " + e.getMessage());
		}

	}

	// TODO To be updated later
	public void updateWisdom(WDWisdomData data) {
		if (data == null) {
			return;
		}
	}

}
