package com.mame.wisdom.search;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
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

}
