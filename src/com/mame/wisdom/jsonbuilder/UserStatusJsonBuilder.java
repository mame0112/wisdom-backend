package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

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

		// param[0], which is user data is mandatory.
		// On the other hand, param[1] and param[2] which is user generated
		// information is
		// optional
		if (param == null || param[0] == null) {
			throw new JSONBuilderException("parameter is null");
		}

		JSONObject paramObj = new JSONObject();

		// Check userId
		if (!(param[0] instanceof Long)) {
			DbgUtil.showLog(TAG, "illegal param type for param[0]");
			throw new JSONBuilderException("illegal param type for param[0]");
		}

		try {
			long userId = (long) param[0];
			paramObj.put(JsonConstant.PARAM_USER_ID, userId);

			// Check total point
			if (!(param[1] instanceof Long)) {
				DbgUtil.showLog(TAG, "illegal param type for param[1]");
				throw new JSONBuilderException(
						"illegal param type for param[1]");
			}

			long totalPoint = (long) param[1];

			paramObj.put(JsonConstant.PARAM_USER_POINT, totalPoint);

			if (param[2] != null) {
				if (!(param[2] instanceof List<?>)) {
					DbgUtil.showLog(TAG, "illegal param type for param[2]");
					throw new JSONBuilderException(
							"illegal param type for param[2]");
				}

				List<WDWisdomData> createdWisdoms = (List<WDWisdomData>) param[2];

				JSONArray createdArray = JsonParseUtil
						.parseWisdomListToJsonArray(createdWisdoms);
				paramObj.put(JsonConstant.PARAM_USER_CREATED_WISDOM,
						createdArray);

			}

			if (param[3] != null) {
				if (!(param[3] instanceof List<?>)) {
					DbgUtil.showLog(TAG, "Illegal param type for param[3]");
					throw new JSONBuilderException(
							"Illegal param type for param[3]");
				}

				List<WDWisdomData> likedWisdoms = (List<WDWisdomData>) param[3];

				JSONArray likeedArray = JsonParseUtil
						.parseWisdomListToJsonArray(likedWisdoms);
				paramObj.put(JsonConstant.PARAM_USER_LIKED_WISDOM, likeedArray);

			}

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
