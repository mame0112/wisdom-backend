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
					String entryName = item
							.getString(JsonConstant.PARAM_WISDOM_ENTRY_NAME);
					int entryType = item
							.getInt(JsonConstant.PARAM_WISDOM_ITEM_TYPE);
					switch (entryType) {
					case WConstant.TAG_WISDOM_MESSAGE:
						// TODO Need to add last user name if necessary
						WDWisdomItemEntry messageEntry = new WDWisdomMessage(i,
								entryName, 0, current, null);
						items.add(messageEntry);
						break;
					case WConstant.TAG_WISDOM_TITLE:
						// TODO Need to add last user name if necessary
						WDWisdomItemEntry titleEntry = new WDWisdomTitle(i,
								entryName, 0, current, null);
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
			String thumbnail = rootObject
					.getString(JsonConstant.PARAM_WISDOM_THUMBNAIL);
			JSONArray messageArray = rootObject
					.getJSONArray(JsonConstant.PARAM_WISDOM_MESSAGES);

			Blob thumbBlob = null;

			if (thumbnail != null) {
				thumbBlob = DatastoreUtil.transcodeString2Blob(thumbnail);
			}

			List<WDWisdomItemEntry> items = createItemEntityListFromJsonArray(messageArray);

			WDWisdomData data = new WDWisdomData(id, title, description, tag,
					createdUserId, lastUpdatedDate, thumbBlob, items);
			WisdomDataStructure structure = new WisdomDataStructure(category,
					subCategory, data);
			return structure;
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}
		return null;

	}
}
