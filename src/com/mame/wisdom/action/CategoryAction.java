package com.mame.wisdom.action;

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

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String params = request.getParameter(WConstant.SERVLET_CATEGORY_PARAM);

		CategoryJsonBuilder builder = new CategoryJsonBuilder();

		if (responseId != null && params != null) {
			builder.addResponseId(Integer.valueOf(responseId));
			WisdomFacade facade = new WisdomFacade();

			JSONObject argObject = new JSONObject(params);
			String category = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_CATEGORY_NAME);
			String subCategory = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_SUB_CATEGORY_NAME);

			if (category != null && subCategory != null) {

				// Get target category data
				WDSubCategoryData categoryData = facade.getCategoryContent(
						category, subCategory);

				if (categoryData != null) {
					List<Long> ids = categoryData.getWisdomIds();

					// Then, get wisdoms belong to this category
					List<WDWisdomData> wisdoms = facade.getWisdomByIds(
							category, subCategory, ids);
					try {
						builder.addResponseParam(categoryData, wisdoms);
					} catch (JSONBuilderException e) {
						DbgUtil.showLog(TAG,
								"JSONBuilderException: " + e.getMessage());
					}

				}

			} else {
				builder.addErrorMessage("category name or sub category name is null");
			}
		} else {
			builder.addErrorMessage("responseId or params is null");
		}

		String result = builder.getResultJson();
		DbgUtil.showLog(TAG, "result: " + result);

		return result;
	}

}
