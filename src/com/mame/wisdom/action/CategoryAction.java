package com.mame.wisdom.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.WisdomFacade;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.jsonbuilder.CategoryJsonBuilder;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.util.DbgUtil;

public class CategoryAction implements Action {

	private final static String TAG = CategoryAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "execute");

		// String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		// TODO
		String responseId = "1";
		String params = request.getParameter(WConstant.SERVLET_PARAMS);

		CategoryJsonBuilder builder = new CategoryJsonBuilder();

		if (responseId != null && params != null) {
			builder.addResponseId(Integer.valueOf(responseId));
			WisdomFacade facade = new WisdomFacade();

			DbgUtil.showLog(TAG, "params: " + params);

			JSONObject argObject = new JSONObject(params);
			String category = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_CATEGORY_NAME);
			String subCategory = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_SUB_CATEGORY_NAME);
			int limit = (int) argObject.get(JsonConstant.PARAM_CATEGORY_LIMIT);
			int offset = (int) argObject
					.get(JsonConstant.PARAM_CATEGORY_OFFSET);

			if (category != null && subCategory != null) {

				// Get target category data
				WDSubCategoryData categoryData = facade.getCategoryContent(
						category, subCategory);

				if (categoryData != null) {
					List<Long> ids = categoryData.getWisdomIds();

					List<Long> input = new ArrayList<Long>();

					int count = 0;

					for (int i = offset; i < (offset + limit); i++) {
						try {
							input.add(ids.get(i));
							count = count + 1;
						} catch (IndexOutOfBoundsException e) {
							// If target range exceeds, we escape from this loop
							break;
						}

					}

					// Then, get wisdoms belong to this category
					List<WDWisdomData> wisdoms = facade.getWisdomByIds(input);
					try {
						builder.addResponseParam(categoryData, wisdoms, offset,
								count);
					} catch (JSONBuilderException e) {
						DbgUtil.showLog(TAG,
								"JSONBuilderException: " + e.getMessage());
					}

				}

			} else {
				DbgUtil.showLog(TAG,
						"category name or sub category name is null");
				builder.addErrorMessage("category name or sub category name is null");
			}
		} else {
			DbgUtil.showLog(TAG, "responseId or params is null");
			builder.addErrorMessage("responseId or params is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}
}
