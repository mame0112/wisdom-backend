package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class UserStatusJsonBuilder extends JsonBuilder {

	private final static String TAG = UserStatusJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public UserStatusJsonBuilder() {
		DbgUtil.showLog(TAG, "UserStatusJsonBuilder");
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
			throw new JSONBuilderException("parameter is null");
		}

		if (!(param[0] instanceof WDUserData)) {
			DbgUtil.showLog(TAG, "Illegal param type for WDUserData");
			throw new JSONBuilderException("Illegal param type for WDUserData");
		}

		if (!(param[1] instanceof List<?>)) {
			DbgUtil.showLog(TAG, "Illegal param type got List<>");
			throw new JSONBuilderException("Illegal param type got List<>");
		}

		WDUserData userData = (WDUserData) param[0];
		List<WDWisdomData> wisdomData = (List<WDWisdomData>) param[1];

		long point = userData.getTotalPoint();

		JSONObject paramObj = new JSONObject();

		try {

			JSONArray messageArray = new JSONArray();

			// TODO Need to consider which item should be returned.
			for (WDWisdomData wisdom : wisdomData) {
				JSONObject message = new JSONObject();
				message.put(JsonConstant.PARAM_WISDOM_TITLE, wisdom.getTitle());
				message.put(JsonConstant.PARAM_WISDOM_THUMBNAIL,
						wisdom.getThumbnakl());
				message.put(JsonConstant.PARAM_WISDOM_TAG, wisdom.getTag());
				message.put(JsonConstant.PARAM_WISDOM_TAG,
						wisdom.getDescription());
				messageArray.put(message);
			}

			paramObj.put(JsonConstant.PARAM_WISDOM_MESSAGES, messageArray);
			paramObj.put(JsonConstant.PARAM_USER_POINT, point);

			mRootObject.put(JsonConstant.PARAMS, paramObj);
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