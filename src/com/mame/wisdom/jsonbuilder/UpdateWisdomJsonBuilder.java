package com.mame.wisdom.jsonbuilder;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class UpdateWisdomJsonBuilder extends JsonBuilder {

	private final static String TAG = UpdateWisdomJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public UpdateWisdomJsonBuilder() {
		DbgUtil.showLog(TAG, "UpdateWisdomJsonBuilder");
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
	public void addResponseParam(Object... param) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseParam");

		if (param == null) {
			throw new IllegalArgumentException("param is null");
		}

		if (!(param[0] instanceof Long)) {
			throw new IllegalArgumentException("Illegal param type");
		}

		if (!(param[1] instanceof Long)) {
			throw new IllegalArgumentException("Illegal param type");
		}

		try {
			long wisdomId = (Long) param[0];
			long point = (Long) param[1];
			JSONArray array = new JSONArray();
			JSONObject resultObject = new JSONObject();
			resultObject.put(JsonConstant.PARAM_WISDOM_ID, wisdomId);
			resultObject.put(JsonConstant.PARAM_USER_POINT, point);

			array.put(resultObject);

			mRootObject.put(JsonConstant.PARAMS, array);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
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
