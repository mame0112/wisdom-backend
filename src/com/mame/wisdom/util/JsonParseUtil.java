package com.mame.wisdom.util;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserStatusData;
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

	// public static JSONObject createJsonObjectFromUserStatus(
	// WDUserStatusData data) {
	// DbgUtil.showLog(TAG, "createJsonObjectFromUserStatus");
	//
	// JSONObject result = new JSONObject();
	//
	// if (data != null) {
	// try {
	// result.put(JsonConstant.PARAM_USER_POINT, data.getTotalPoint());
	// result.put(JsonConstant.PARAM_USER_ID, data.getUserId());
	//
	// return result;
	//
	// } catch (JSONException e) {
	// DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
	// }
	//
	// }
	//
	// return null;
	// }

	public static List<WDWisdomItemEntry> createWisdomItemEntryListFromJsonString(
			String originalJson) {
		DbgUtil.showLog(TAG, "createWisdomItemEntryListFromJsonString");

		if (originalJson != null) {
			JSONArray array;
			try {
				array = new JSONArray(originalJson);
				return createWisdomItemEntryListFromJsonArray(array);
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}
		}

		return null;
	}

	public static JSONArray parseWisdomItemEntitiesToJsonArray(
			List<WDWisdomItemEntry> items) {
		DbgUtil.showLog(TAG, "parseWisdomItemEntitiesToJsonArray");

		if (items != null) {
			JSONArray rootArray = new JSONArray();

			for (WDWisdomItemEntry item : items) {
				JSONObject object = parseWDWisdomItemEntryToJsonObject(item);
				rootArray.put(object);
			}

			return rootArray;
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
				obj.put(JsonConstant.PARAM_WISDOM_MESSAGES,
						parseWisdomItemEntitiesToJsonArray(wisdom.getItems())
								.toString());
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

	public static WDWisdomData createWisdomDataFromJson(String jsonString) {

		DbgUtil.showLog(TAG, "createWisdomDataFromJson: " + jsonString);

		if (jsonString != null) {

			try {
				JSONObject rootObject = new JSONObject(jsonString);
				int id = rootObject.getInt(JsonConstant.ID);
				JSONArray messageArray = rootObject
						.getJSONArray(JsonConstant.PARAM_WISDOM_MESSAGES);
				List<WDWisdomItemEntry> messages = createWisdomItemEntryListFromJsonArray(messageArray);
				String title = rootObject
						.getString(JsonConstant.PARAM_WISDOM_TITLE);
				String category = rootObject
						.getString(JsonConstant.PARAM_WISDOM_CATEGORY);
				String subCategory = rootObject
						.getString(JsonConstant.PARAM_WISDOM_SUB_CATEGORY);
				long updateDate = rootObject
						.getLong(JsonConstant.PARAM_WISDOM_UPDATED_DATE);
				int count = rootObject
						.getInt(JsonConstant.PARAM_WISDOM_VIEW_COUNT);
				String description = rootObject
						.getString(JsonConstant.PARAM_WISDOM_DESCRIPTION);
				String tag = rootObject
						.getString(JsonConstant.PARAM_WISDOM_TAG);
				long createUserId = rootObject
						.getLong(JsonConstant.PARAM_WISDOM_CREATE_USER_ID);
				return new WDWisdomData(id, title, description, tag,
						createUserId, updateDate, null, messages, count,
						category, subCategory);
			} catch (JSONException e) {
				DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			}

			// List<WDWisdomItemEntry> createWisdomItemEntryListFromJson
		}

		return null;
	}

	private static List<WDWisdomItemEntry> createWisdomItemEntryListFromJsonArray(
			JSONArray array) {
		DbgUtil.showLog(TAG, "createWisdomItemEntryListFromJsonArray");
		try {

			List<WDWisdomItemEntry> result = new ArrayList<WDWisdomItemEntry>();

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
					likeNum = obj.getInt(JsonConstant.PARAM_WISDOM_ITEM_LIKE);
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
		return null;
	}
}
