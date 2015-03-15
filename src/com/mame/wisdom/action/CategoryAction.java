package com.mame.wisdom.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.datastore.WisdomFacade;
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

		DbgUtil.showLog(TAG, "responseId: " + responseId);
		DbgUtil.showLog(TAG, "params: " + params);
		if (responseId != null && params != null) {
			builder.addResponseId(Integer.valueOf(responseId));
			WisdomFacade facade = new WisdomFacade();

			JSONObject argObject = new JSONObject(params);
			String category = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_CATEGORY_NAME);
			String subCategory = (String) argObject
					.get(JsonConstant.PARAM_CATEGORY_SUB_CATEGORY_NAME);
			if (category != null && subCategory != null) {
				List<WDSubCategoryData> categories = facade.getCategoryContent(
						category, subCategory);
				builder.addResponseParam(categories);
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
