package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class CategoryJsonBuilder extends JsonBuilder {

	private final static String TAG = CategoryJsonBuilder.class.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public CategoryJsonBuilder() {
		DbgUtil.showLog(TAG, "CategoryJsonBuilder");
		try {
			addVersion(VERSION);
		} catch (JSONBuilderException e) {
			DbgUtil.showLog(TAG, "JSONBuilderException: " + e.getMessage());
		}
	}

	@Override
	public void addResponseId(int id) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseId");

		try {
			mRootObject.put(JsonConstant.ID, id);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}

	}

	@Override
	protected void addVersion(String version) throws JSONBuilderException {
		try {
			mRootObject.put(JsonConstant.VERSION, version);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			throw new JSONBuilderException(e.getMessage());
		}
	}

	@Override
	public void addResponseParam(Object param) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseParam");
		if (param == null) {
			throw new JSONBuilderException("param is null");
		}
		if (!(param instanceof List<?>)) {
			throw new JSONBuilderException("Illegal param type");
		}

		List<WDSubCategoryData> categories = (List<WDSubCategoryData>) param;

		JSONObject resp = new JSONObject();

		for (WDSubCategoryData category : categories) {
			try {
				resp.put(JsonConstant.PARAM_CATEGORY_CATEGORY_ID,
						category.getCategoryId());
				resp.put(JsonConstant.PARAM_CATEGORY_SUBCATEGORY_ID,
						category.getSubCategoryId());
				resp.put(JsonConstant.PARAM_CATEGORY_CATEGORY_NAME,
						category.getCategoryName());
				resp.put(JsonConstant.PARAM_CATEGORY_SUB_CATEGORY_NAME,
						category.getSubCategoryName());
				resp.put(JsonConstant.PARAM_CATEGORY_DESCRIPTION,
						category.getDescription());
				List<Long> wisdomIds = category.getWisdomIds();

				// TODO
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}

		}

		try {
			mRootObject.put(JsonConstant.PARAMS, resp);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			throw new JSONBuilderException(e.getMessage());
		}

	}

	@Override
	public void addErrorMessage(String message) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addErrorMessage");
		try {
			mRootObject.put(JsonConstant.PARAM_ERROR_MESSAGE, message);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}

	}

	@Override
	public String getResultJson() throws JSONBuilderException {
		DbgUtil.showLog(TAG, "getResultJson");
		return mRootObject.toString();
	}

}
