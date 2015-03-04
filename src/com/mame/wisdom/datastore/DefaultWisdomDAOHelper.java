package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.util.DbgUtil;

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
					.getProperty(DBConstant.ENTITY_WISDOM_Thumbnail);
			List<WDWisdomItemEntry> items = (List<WDWisdomItemEntry>) e
					.getProperty(DBConstant.ENTITY_WISDOM_ITMES);

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

			List<WDWisdomData> result = new ArrayList<WDWisdomData>();

			for (Entity e : entities) {
				WDWisdomData data = parseEntityToWDWisdomData(e);
				result.add(data);
			}
			return result;
		}

		return null;
	}
}
