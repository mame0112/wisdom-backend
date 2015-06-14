package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.data.WDWisdomMessage;
import com.mame.wisdom.data.WDWisdomTitle;
import com.mame.wisdom.data.WisdomDataStructure;
import com.mame.wisdom.jsonbuilder.JsonConstant;
import com.mame.wisdom.util.DatastoreUtil;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.TimeUtil;

public class WisdomFacadeHelper {

	private final static String TAG = WisdomFacadeHelper.class.getSimpleName();

	/**
	 * Package private
	 */
	WisdomFacadeHelper() {
		DbgUtil.showLog(TAG, "WisdomFacadeHelper constructor");
	}

	List<WDWisdomItemEntry> createItemEntityListFromJsonArray(
			JSONArray contentArray) {
		DbgUtil.showLog(TAG, "createItemEntityListFromJsonArray");

		if (contentArray != null) {
			List<WDWisdomItemEntry> items = new ArrayList<WDWisdomItemEntry>();

			long current = TimeUtil.getCurrentDate();

			for (int i = 0; i < contentArray.length(); i++) {
				try {
					JSONObject item = (JSONObject) contentArray.get(i);
					String entryContent = item
							.getString(JsonConstant.PARAM_WISDOM_ENTRY_NAME);
					int entryType = item
							.getInt(JsonConstant.PARAM_WISDOM_ITEM_TYPE);

					// long id = (Long)
					// item.getLong(JsonConstant.PARAM_WISDOM_ID);
					// int numOfLike = (Integer) item
					// .getInt(JsonConstant.PARAM_WISDOM_ITEM_LIKE);
					long lastUpdateuserId = WConstant.NO_USER;
					try {
						lastUpdateuserId = (Long) item
								.getLong(JsonConstant.PARAM_WISDOM_ITEM_UPDATE_USER_ID);
					} catch (JSONException e) {
						DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
						// Ignore because last update user id is optional
					}

					String lastUpdateUserName = null;
					try {
						lastUpdateUserName = (String) item
								.getString(JsonConstant.PARAM_WISDOM_ITEM_UPDAtE_USER_NAME);
					} catch (JSONException e) {
						DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
						// Ignore because last update user id is optional
					}

					switch (entryType) {
					case WConstant.TAG_WISDOM_MESSAGE:
						// TODO Need to add last user name if necessary
						WDWisdomItemEntry messageEntry = new WDWisdomMessage(
								WConstant.NO_WISDOM, entryContent, 0,
								lastUpdateuserId, lastUpdateUserName, current);

						// long id, String message, int numOfLike,
						// long lastUpdateUserId, String lastUpdateUserName,
						// long updateDate

						items.add(messageEntry);
						break;
					case WConstant.TAG_WISDOM_TITLE:
						// TODO Need to add last user name if necessary
						WDWisdomItemEntry titleEntry = new WDWisdomTitle(
								WConstant.NO_WISDOM, entryContent, 0,
								lastUpdateuserId, lastUpdateUserName, current);
						items.add(titleEntry);
						break;
					default:
						DbgUtil.showLog(TAG, "Illegal entry type: " + entryType);
						break;
					}
				} catch (JSONException e) {
					DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
				}

			}

			return items;

		}

		return null;
	}

	WisdomDataStructure createWisdomDataFromInputString(String input) {
		DbgUtil.showLog(TAG, "createWisdomDataFromInputString");
		try {
			JSONObject rootObject = new JSONObject(input);
			long id = rootObject.getLong(JsonConstant.PARAM_WISDOM_ID);
			String title = rootObject
					.getString(JsonConstant.PARAM_WISDOM_TITLE);
			String description = rootObject
					.getString(JsonConstant.PARAM_WISDOM_DESCRIPTION);
			String category = rootObject
					.getString(JsonConstant.PARAM_WISDOM_CATEGORY);
			String subCategory = rootObject
					.getString(JsonConstant.PARAM_WISDOM_SUB_CATEGORY);
			String tag = rootObject.getString(JsonConstant.PARAM_WISDOM_TAG);
			long createdUserId = rootObject
					.getLong(JsonConstant.PARAM_WISDOM_CREATE_USER_ID);
			long lastUpdatedDate = rootObject
					.getLong(JsonConstant.PARAM_WISDOM_UPDATED_DATE);

			JSONArray messageArray = rootObject
					.getJSONArray(JsonConstant.PARAM_WISDOM_MESSAGES);
			List<WDWisdomItemEntry> items = createItemEntityListFromJsonArray(messageArray);

			Blob thumbBlob = null;
			// THumbnail is an optional parameter. THen, we should ignore even
			// if it doesn't exist
			try {
				String thumbnail = rootObject
						.getString(JsonConstant.PARAM_WISDOM_THUMBNAIL);

				if (thumbnail != null) {
					thumbBlob = DatastoreUtil.transcodeString2Blob(thumbnail);
				}
			} catch (JSONException e1) {
				DbgUtil.showLog(TAG,
						"JSONException for thumbnail: " + e1.getMessage());
			}

			DbgUtil.showLog(TAG, "createdUserId: " + createdUserId);
			DbgUtil.showLog(TAG, "lastUpdatedDate: " + lastUpdatedDate);

			WDWisdomData data = new WDWisdomData(id, title, description, tag,
					createdUserId, lastUpdatedDate, thumbBlob, items, 0L);
			WisdomDataStructure structure = new WisdomDataStructure(category,
					subCategory, data);
			return structure;
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}
		return null;

	}
}
