package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

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

			String entryJson = (String) e
					.getProperty(DBConstant.ENTITY_WISDOM_ITMES);
			List<WDWisdomItemEntry> items = JsonParseUtil
					.createWisdomItemEntryListFromJson(entryJson);

			WDWisdomData data = new WDWisdomData(wisdomId, title, description,
					tag, createdUserId, lastUpdatedDate, thumbnail, items);
			return data;
		}

		return null;
	}

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
			String itemsJSON = JsonParseUtil.parseWisdomItemEntitiesToJson(data
					.getItems());

			entity.setProperty(DBConstant.ENTITY_WISDOM_ID, data.getWisdomId());
			entity.setProperty(DBConstant.ENTITY_WISDOM_CREATED_USER_ID,
					data.getCreatedUserId());
			entity.setProperty(DBConstant.ENTITY_WISDOM_DESCRIPTION,
					data.getDescription());
			entity.setProperty(DBConstant.ENTITY_WISDOM_ITMES, itemsJSON);
			entity.setProperty(DBConstant.ENTITY_WISDOM_LAST_UPDATED_DATE,
					data.getLastUpdatedDate());
			entity.setProperty(DBConstant.ENTITY_WISDOM_TAG, data.getTag());
			entity.setProperty(DBConstant.ENTITY_WISDOM_THUMBNAIL,
					data.getThumbnakl());
			entity.setProperty(DBConstant.ENTITY_WISDOM_TITLE, data.getTitle());
			return entity;
		} else {
			DbgUtil.showLog(TAG, "data or entity is null");
		}
		return null;
	}
}
