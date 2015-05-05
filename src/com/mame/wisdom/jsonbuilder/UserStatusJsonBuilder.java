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

		if (!(param[0] instanceof Long)) {
			DbgUtil.showLog(TAG, "illegal param type for param[0]");
			throw new JSONBuilderException("illegal param type for param[0]");
		}

		long totalPoint = (long) param[0];

		JSONObject paramObj = new JSONObject();

		try {
			paramObj.put(JsonConstant.PARAM_USER_POINT, totalPoint);

			if (param[1] != null) {
				if (!(param[1] instanceof List<?>)) {
					DbgUtil.showLog(TAG, "illegal param type for param[1]");
					throw new JSONBuilderException(
							"illegal param type for param[1]");
				}

				List<WDWisdomData> createdWisdoms = (List<WDWisdomData>) param[1];

				JSONArray createdArray = JsonParseUtil
						.parseWisdomListToJsonArray(createdWisdoms);
				paramObj.put(JsonConstant.PARAM_USER_CREATED_WISDOM,
						createdArray);

			}

			if (param[2] != null) {
				if (!(param[2] instanceof List<?>)) {
					DbgUtil.showLog(TAG, "Illegal param type for param[2]");
					throw new JSONBuilderException(
							"Illegal param type for param[2]");
				}

				List<WDWisdomData> likedWisdoms = (List<WDWisdomData>) param[2];

				JSONArray likeedArray = JsonParseUtil
						.parseWisdomListToJsonArray(likedWisdoms);
				paramObj.put(JsonConstant.PARAM_USER_LIKED_WISDOM, likeedArray);

			}

			// paramObj.put(JsonConstant.PARAM_USER_LIKED_WISDOM, totalPoint);

			// // If user has already generated some information
			// if (param[1] != null) {
			//
			// List<WDWisdomData> wisdomData = (List<WDWisdomData>) param[1];
			// JSONArray messageArray = new JSONArray();
			//
			// // TODO Need to consider which item should be returned.
			// for (WDWisdomData wisdom : wisdomData) {
			// JSONObject message = new JSONObject();
			// message.put(JsonConstant.PARAM_WISDOM_TITLE,
			// wisdom.getTitle());
			// message.put(JsonConstant.PARAM_WISDOM_THUMBNAIL,
			// wisdom.getThumbnail());
			// message.put(JsonConstant.PARAM_WISDOM_TAG, wisdom.getTag());
			// message.put(JsonConstant.PARAM_WISDOM_DESCRIPTION,
			// wisdom.getDescription());
			// // TODO Need to return "Created date" (Not last updated
			// // date)
			// message.put(JsonConstant.PARAM_WISDOM_UPDATED_DATE,
			// wisdom.getLastUpdatedDate());
			// messageArray.put(message);
			// }
			// paramObj.put(JsonConstant.PARAM_WISDOM_MESSAGES, messageArray);
			//
			// } else {
			// DbgUtil.showLog(TAG, "wisdomData is null");
			// }

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
