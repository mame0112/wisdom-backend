package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDHighlighPointUserData;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class UserRankingJsonBuilder extends JsonBuilder {

	private final static String TAG = UserRankingJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public UserRankingJsonBuilder() {
		DbgUtil.showLog(TAG, "UserRankingJsonBuilder");

		try {
			addVersion(VERSION);
		} catch (JSONBuilderException e) {
			DbgUtil.showLog(TAG, "JSONBuilderException: " + e.getMessage());
		}

	}

	@Override
	public void addResponseId(int id) throws JSONBuilderException {
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
		DbgUtil.showLog(TAG, "UserRankingJsonBuilder addResponseParam");

		if (param == null || param[0] == null) {
			throw new JSONBuilderException("param is null");
		}

		if (!(param[0] instanceof List<?>)) {
			throw new JSONBuilderException(
					"UserRankingJsonBuilder Illegal param type");
		}

		List<WDHighlighPointUserData> users = (List<WDHighlighPointUserData>) param[0];

		try {
			JSONArray array = new JSONArray();

			for (WDHighlighPointUserData user : users) {
				JSONObject obj = new JSONObject();

				String name = user.getUserName();

				// if (user.getTwitterName() != null) {
				// DbgUtil.showLog(TAG, "Twitter");
				// name = user.getTwitterName();
				// } else {
				// if (user.getFacebookName() != null) {
				// DbgUtil.showLog(TAG, "facebook");
				// name = user.getFacebookName();
				// } else {
				// if (user.getUsername() != null) {
				// DbgUtil.showLog(TAG, "usernmae");
				// name = user.getUsername();
				// }
				// }
				// }

				// If no name available, we should not return it
				if (name != null) {
					obj.put(JsonConstant.PARAM_USER_NAME, name);
					obj.put(JsonConstant.PARAM_USER_POINT, user.getPoint());
					obj.put(JsonConstant.PARAM_USER_ID, user.getUserId());
					// obj.put(JsonConstant.PARAM_USER_THUMBNAIL,
					// user.getThumbnail());
					array.put(obj);
				}

			}

			mRootObject.put(JsonConstant.PARAMS, array);
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
