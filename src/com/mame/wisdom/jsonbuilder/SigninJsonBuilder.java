package com.mame.wisdom.jsonbuilder;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class SigninJsonBuilder extends JsonBuilder {

	private final static String TAG = SigninJsonBuilder.class.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public SigninJsonBuilder() {
		DbgUtil.showLog(TAG, "SigninJsonBuilder");
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
	public void addResponseParam(Object param) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseParam");

		if (param == null) {
			throw new JSONBuilderException("param is null");
		}

		// If instance type is not correct
		if (!(param instanceof WDUserData)) {
			throw new JSONBuilderException("illegal instance type");
		}

		WDUserData data = (WDUserData) param;
		JSONObject paramObject = new JSONObject();

		try {
			paramObject.put(JsonConstant.PARAM_USER_ID, data.getUserId());
			paramObject.put(JsonConstant.PARAM_USER_TWITTER_NAME,
					data.getTwitterName());
			mRootObject.put(JsonConstant.PARAMS, paramObject);
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
	public String getResultJson() throws JSONBuilderException {
		DbgUtil.showLog(TAG, "getResultJson");
		return mRootObject.toString();
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
}
