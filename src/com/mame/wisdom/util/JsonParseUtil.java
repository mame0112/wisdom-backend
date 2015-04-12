package com.mame.wisdom.util;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.data.WDWisdomMessage;
import com.mame.wisdom.data.WDWisdomTitle;
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

	public static List<WDWisdomItemEntry> createWisdomItemEntryListFromJson(
			String originalJson) {
		DbgUtil.showLog(TAG, "createWisdomItemEntryListFromJson");

		if (originalJson != null) {
			List<WDWisdomItemEntry> result = new ArrayList<WDWisdomItemEntry>();

			try {

				JSONArray array = new JSONArray(originalJson);

				for (int i = 0; i < array.length(); i++) {

					JSONObject obj = array.getJSONObject(i);
					String message = obj
							.getString(JsonConstant.PARAM_WISDOM_ITEM_MESSAGE);
					DbgUtil.showLog(TAG, "message:" + message);
					long id = obj.getLong(JsonConstant.PARAM_WISDOM_ITEM_ID);

					long updateTime = 0;
					try {
						updateTime = obj
								.getLong(JsonConstant.PARAM_WISDOM_UPDATED_DATE);
					} catch (JSONException e) {
						// Ignore since this is optional parameter.
					}

					long updateUserId = WConstant.NO_USER;
					try {
						updateUserId = obj
								.getLong(JsonConstant.PARAM_WISDOM_ITEM_UPDATE_USER_ID);
					} catch (JSONException e) {
						// Ignore since this is optional parameter.
					}

					String updateUserName = null;

					try {
						updateUserName = obj
								.getString(JsonConstant.PARAM_WISDOM_ITEM_UPDAtE_USER_NAME);
					} catch (JSONException e) {
						// Ignore since this is optional parameter.
					}

					int likeNum = 0;
					try {
						likeNum = obj
								.getInt(JsonConstant.PARAM_WISDOM_ITEM_LIKE);
					} catch (JSONException e) {
						// Ignore since this is optional parameter.
					}

					int tag = obj.getInt(JsonConstant.PARAM_WISDOM_TAG);

					WDWisdomItemEntry entry = null;

					switch (tag) {
					case WConstant.TAG_WISDOM_MESSAGE:
						entry = new WDWisdomMessage(id, message, likeNum,
								updateUserId, updateUserName, updateTime);
						result.add(entry);
						break;
					case WConstant.TAG_WISDOM_TITLE:
						entry = new WDWisdomTitle(id, message, likeNum,
								updateUserId, updateUserName, updateTime);
						result.add(entry);
						break;
					default:
						DbgUtil.showLog(TAG, "Illegal tag id");
						break;
					}
				}
				return result;
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}
		}

		return null;
	}

	public static String parseWisdomItemEntitiesToJson(
			List<WDWisdomItemEntry> items) {
		DbgUtil.showLog(TAG, "parseWisdomItemEntitiesToJson");

		if (items != null) {
			JSONArray rootArray = new JSONArray();

			for (WDWisdomItemEntry item : items) {
				JSONObject object = parseWDWisdomItemEntryToJsonObject(item);
				rootArray.put(object);
			}

			return rootArray.toString();
		}

		return null;

	}

	public static JSONObject parseWDWisdomItemEntryToJsonObject(
			WDWisdomItemEntry item) {
		DbgUtil.showLog(TAG, "parseWDWisdomItemEntryToJsonObject");
		if (item != null) {
			JSONObject object = new JSONObject();

			try {
				object.put(JsonConstant.PARAM_WISDOM_ITEM_MESSAGE,
						item.getItem());
				object.put(JsonConstant.PARAM_WISDOM_ITEM_ID, item.getItemId());

				object.put(JsonConstant.PARAM_WISDOM_ITEM_UPDATE_USER_ID,
						item.getLastUpdateUserId());
				object.put(JsonConstant.PARAM_WISDOM_ITEM_UPDAtE_USER_NAME,
						item.getLastUpdateUserName());
				object.put(JsonConstant.PARAM_WISDOM_ITEM_LIKE,
						item.getNumberOfLike());
				object.put(JsonConstant.PARAM_WISDOM_UPDATED_DATE,
						item.getLastUpdateDate());
				object.put(JsonConstant.PARAM_WISDOM_TAG, item.getTag());
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException");
				return null;
			}

			return object;
		}
		return null;
	}

	public static JSONArray parseWisdomListToJsonArray(
			List<WDWisdomData> wisdoms) {
		DbgUtil.showLog(TAG, "parseWisdomListToJsonArray");

		if (wisdoms != null) {

			JSONArray array = new JSONArray();

			for (WDWisdomData data : wisdoms) {
				// Put completed object to array
				array.put(parseWisdomDataToJsonObject(data));
			}

			return array;
		}

		return null;
	}

	public static JSONObject parseWisdomDataToJsonObject(WDWisdomData wisdom) {
		DbgUtil.showLog(TAG, "parseWisdomDataToJsonObject");

		if (wisdom != null) {
			try {
				JSONObject obj = new JSONObject();
				obj.put(JsonConstant.PARAM_WISDOM_ITEM_MESSAGE, JsonParseUtil
						.parseWisdomItemEntitiesToJson(wisdom.getItems()));
				obj.put(JsonConstant.PARAM_WISDOM_ID, wisdom.getWisdomId());

				obj.put(JsonConstant.PARAM_WISDOM_TITLE, wisdom.getTitle());
				obj.put(JsonConstant.PARAM_WISDOM_TAG, wisdom.getTag());
				obj.put(JsonConstant.PARAM_WISDOM_DESCRIPTION,
						wisdom.getDescription());
				obj.put(JsonConstant.PARAM_WISDOM_UPDATED_DATE,
						wisdom.getLastUpdatedDate());
				obj.put(JsonConstant.PARAM_WISDOM_CREATE_USER_ID,
						wisdom.getCreatedUserId());
				obj.put(JsonConstant.PARAM_WISDOM_VIEW_COUNT,
						wisdom.getViewCount());
				return obj;

			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}
		}

		return null;
	}

}
