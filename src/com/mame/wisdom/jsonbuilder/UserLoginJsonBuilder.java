package com.mame.wisdom.jsonbuilder;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

public class UserLoginJsonBuilder extends JsonBuilder {

	private final static String TAG = UserLoginJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public UserLoginJsonBuilder() {
		DbgUtil.showLog(TAG, "UserLoginJsonBuilder");
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

		if (param != null && param[0] != null) {
			if (!(param[0] instanceof WDUserData)) {
				throw new JSONBuilderException("Illegal parameter type");
			}

			try {
				mRootObject.put(JsonConstant.PARAMS, JsonParseUtil
						.createJsonObjectFromUserData((WDUserData) param[0]));
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}
		} else {
			try {
				DbgUtil.showLog(TAG, "AAA");
				mRootObject.put(JsonConstant.PARAMS, new JSONObject());
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}
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
