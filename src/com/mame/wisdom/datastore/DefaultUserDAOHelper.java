package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.util.DbgUtil;

public class DefaultUserDAOHelper {

	private final static String TAG = DefaultUserDAOHelper.class
			.getSimpleName();

	public List<WDUserData> parseEntityListToUserDataList(List<Entity> entities) {
		DbgUtil.showLog(TAG, "parseEntityListToUserDataList");

		if (entities != null) {
			List<WDUserData> result = new ArrayList<WDUserData>();
			for (Entity e : entities) {
				result.add(constructUserDataFromEntity(e));
			}
			return result;
		}

		return null;
	}

	public WDUserData constructUserDataFromEntity(Entity entity) {
		DbgUtil.showLog(TAG, "constructUserDataFromEntity");

		long userId = (Long) entity.getProperty(DBConstant.ENTITY_USER_ID);
		String twitterName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_TWITTER_NAME);
		String twitterToken = (String) entity
				.getProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN);
		String twitterTokenSecret = (String) entity
				.getProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN_SECRET);
		String facebookName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_FACEBOOK_NAME);
		String userName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_NAME);
		String password = (String) entity
				.getProperty(DBConstant.ENTITY_USER_PASSWORD);
		String thumbnail = (String) entity
				.getProperty(DBConstant.ENTITY_USER_THUMBNAIL);
		long lastLogin = (Long) entity
				.getProperty(DBConstant.ENTITY_USER_LAST_LOGIN);
		long totalPoint = (Long) entity
				.getProperty(DBConstant.ENTITY_USER_TOTAL_POINT);

		WDUserData data = new WDUserData(userId, twitterName, twitterToken,
				twitterTokenSecret, facebookName, userName, password,
				thumbnail, lastLogin, totalPoint);

		return data;
	}

}