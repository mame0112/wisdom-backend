package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class FacebookSignupJsonBuilder extends JsonBuilder {

	private final static String TAG = FacebookSignupJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public FacebookSignupJsonBuilder() {
		DbgUtil.showLog(TAG, "FacebookSignupJsonBuilder");
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
		DbgUtil.showLog(TAG, "addVersion");
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
			throw new JSONBuilderException("param is null");
		}

		// If instance type is not correct
		if (!(param[0] instanceof Long)) {
			throw new JSONBuilderException("illegal instance type");
		}

		long userId = (Long) param[0];

		JSONArray itemArray = new JSONArray();
		JSONObject object = new JSONObject();
		try {
			object.put(JsonConstant.PARAM_USER_ID, userId);
			itemArray.put(object);
			mRootObject.put(JsonConstant.PARAMS, itemArray);
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
