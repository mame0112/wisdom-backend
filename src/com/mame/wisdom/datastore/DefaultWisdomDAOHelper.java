package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomDataBuilder;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;
import com.mame.wisdom.util.TimeUtil;

public class DefaultWisdomDAOHelper {

	private final static String TAG = DefaultWisdomDAOHelper.class
			.getSimpleName();

	public WDWisdomData parseEntityToWDWisdomData(Entity e) {
		DbgUtil.showLog(TAG, "parseEntityToWDWisdomData");

		if (e != null) {
			long wisdomId = (Long) e.getProperty(DBConstant.ENTITY_WISDOM_ID);
			String title = (String) e
					.getProperty(DBConstant.ENTITY_WISDOM_TITLE);
			String description = (String) e
					.getProperty(DBConstant.ENTITY_WISDOM_DESCRIPTION);
			String tag = (String) e.getProperty(DBConstant.ENTITY_WISDOM_TAG);
			long createdUserId = (Long) e
					.getProperty(DBConstant.ENTITY_WISDOM_CREATED_USER_ID);
			long lastUpdatedDate = (Long) e
					.getProperty(DBConstant.ENTITY_WISDOM_LAST_UPDATED_DATE);
			Blob thumbnail = (Blob) e
					.getProperty(DBConstant.ENTITY_WISDOM_THUMBNAIL);

			long viewCount = 0;

			try {
				viewCount = (Long) e
						.getProperty(DBConstant.ENTITY_WISDOM_VIEWED_COUNT);
			} catch (Exception ex) {
				DbgUtil.showLog(TAG, "Exception: " + ex.getMessage());
			}

			String entryJson = ((Text) e
					.getProperty(DBConstant.ENTITY_WISDOM_ITMES)).getValue();
			List<WDWisdomItemEntry> items = JsonParseUtil
					.createWisdomItemEntryListFromJsonString(entryJson);

			WDWisdomDataBuilder builder = WDWisdomDataBuilder.createFrom(null);
			return builder.setWisdomId(wisdomId).setTitle(title)
					.setDescription(description).setTag(tag)
					.setCreatedUserId(createdUserId)
					.setLastUpdatedDate(lastUpdatedDate)
					.setThumbnail(thumbnail).setViewCount(viewCount)
					.setEntires(items).getWisdomData();
			// WDWisdomData data = new WDWisdomData(wisdomId, title,
			// description,
			// tag, createdUserId, lastUpdatedDate, thumbnail, items,
			// viewCount);
			// return data;
		}

		return null;
	}

	// private List<Long> createWisdomMessageOrder(List<WDWisdomItemEntry>
	// items) {
	//
	// if (items != null) {
	// List<Long> ids = new ArrayList<Long>();
	// for (WDWisdomItemEntry item : items) {
	// ids.add(item.getItemId());
	// }
	//
	// return ids;
	// }
	//
	// return null;
	// }

	public List<WDWisdomData> parseListEntityToWDWisdomData(
			List<Entity> entities) {
		DbgUtil.showLog(TAG, "parseListEntityToWDWisdomData");

		if (entities != null && entities.size() != 0) {
			DbgUtil.showLog(TAG, "CC");

			List<WDWisdomData> result = new ArrayList<WDWisdomData>();

			for (Entity e : entities) {
				WDWisdomData data = parseEntityToWDWisdomData(e);
				result.add(data);
			}
			return result;
		}

		return null;
	}

	public Entity parseWisdomDataToEntity(WDWisdomData data, Entity entity) {
		DbgUtil.showLog(TAG, "parseWisdomDataToEntity");

		if (data != null && entity != null) {

			// Get entities belong to one wisdom
			JSONArray itemsJSON = JsonParseUtil
					.parseWisdomItemEntitiesToJsonArray(data.getItems());

			entity.setProperty(DBConstant.ENTITY_WISDOM_ID, data.getWisdomId());
			entity.setProperty(DBConstant.ENTITY_WISDOM_CREATED_USER_ID,
					data.getCreatedUserId());
			entity.setProperty(DBConstant.ENTITY_WISDOM_DESCRIPTION,
					data.getDescription());
			// entity.setProperty(DBConstant.ENTITY_WISDOM_ITMES,
			// itemsJSON.toString());
			if (itemsJSON != null) {
				entity.setProperty(DBConstant.ENTITY_WISDOM_ITMES, new Text(
						itemsJSON.toString()));
			}

			entity.setProperty(DBConstant.ENTITY_WISDOM_LAST_UPDATED_DATE,
					data.getLastUpdatedDate());
			entity.setProperty(DBConstant.ENTITY_WISDOM_TAG, data.getTag());
			entity.setProperty(DBConstant.ENTITY_WISDOM_THUMBNAIL,
					data.getThumbnail());
			entity.setProperty(DBConstant.ENTITY_WISDOM_TITLE, data.getTitle());
			entity.setProperty(DBConstant.ENTITY_WISDOM_VIEWED_COUNT,
					data.getViewCount());
			return entity;
		} else {
			DbgUtil.showLog(TAG, "data or entity is null");
		}
		return null;
	}
}
