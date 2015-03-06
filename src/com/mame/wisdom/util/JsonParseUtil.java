package com.mame.wisdom.util;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.jsonbuilder.JsonConstant;

public class JsonParseUtil {
	private final static String TAG = JsonParseUtil.class.getSimpleName();

	public static JSONObject createJsonObjectFromUserData(WDUserData data) {
		DbgUtil.showLog(TAG, "createJsonObjectFromUserData");

		JSONObject result = new JSONObject();

		if (data != null) {
			try {
				result.put(JsonConstant.PARAM_USER_FACEBOOK_NAME,
						data.getFacebookName());
				result.put(JsonConstant.PARAM_USER_LAST_LOGIN,
						data.getLastLoginDate());
				result.put(JsonConstant.PARAM_USER_PASSWORD, data.getPassword());
				result.put(JsonConstant.PARAM_USER_THUMBNAIL,
						data.getThumbnail());
				result.put(JsonConstant.PARAM_USER_POINT, data.getTotalPoint());
				result.put(JsonConstant.PARAM_USER_TWITTER_NAME,
						data.getTwitterName());
				result.put(JsonConstant.PARAM_USER_TWITTER_TOKEN,
						data.getTwitterToken());
				result.put(JsonConstant.PARAM_USER_TWITTER_TOKEN_SECRET,
						data.getTwitterTokenSecret());
				result.put(JsonConstant.PARAM_USER_ID, data.getUserId());
				result.put(JsonConstant.PARAM_USER_NAME, data.getUsername());

				return result;

			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}

		}

		return null;
	}
}
