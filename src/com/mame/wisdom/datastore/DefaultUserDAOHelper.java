package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.mame.wisdom.constant.WConstant;
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

		if (entity != null) {
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
			String mailAddress = (String) entity
					.getProperty(DBConstant.ENTITY_USER_MAIL_ADDRESS);

			WDUserData data = new WDUserData(userId, twitterName, twitterToken,
					twitterTokenSecret, facebookName, userName, password,
					thumbnail, lastLogin, totalPoint, mailAddress);

			return data;
		} else {
			DbgUtil.showLog(TAG, "Entity is null");
			return null;
		}

	}

	public static Entity createEntityFromUserData(WDUserData data) {
		if (data != null) {
			long userId = data.getUserId();
			String userName = data.getUsername();
			String password = data.getPassword();
			String twitter = data.getTwitterName();
			String twitterToken = data.getTwitterToken();
			String twitterTokenSecret = data.getTwitterTokenSecret();
			String facebook = data.getFacebookName();
			String thumbnail = data.getThumbnail();
			long lastLogin = data.getLastLoginDate();
			long totalPoint = data.getTotalPoint();
			String mailAddress = data.getMailAddress();

			Key ancKey = DatastoreKeyGenerator.getAllUserDataKey();
			Entity entity = new Entity(DBConstant.KIND_USER_DATA, userId,
					ancKey);

			entity.setProperty(DBConstant.ENTITY_USER_ID, userId);
			entity.setProperty(DBConstant.ENTITY_USER_NAME, userName);
			entity.setProperty(DBConstant.ENTITY_USER_PASSWORD, password);
			entity.setProperty(DBConstant.ENTITY_USER_TWITTER_NAME, twitter);
			entity.setProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN,
					twitterToken);
			entity.setProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN_SECRET,
					twitterTokenSecret);
			entity.setProperty(DBConstant.ENTITY_USER_FACEBOOK_NAME, facebook);
			entity.setProperty(DBConstant.ENTITY_USER_THUMBNAIL, thumbnail);
			entity.setProperty(DBConstant.ENTITY_USER_TOTAL_POINT, totalPoint);
			entity.setProperty(DBConstant.ENTITY_USER_LAST_LOGIN, lastLogin);
			entity.setProperty(DBConstant.ENTITY_USER_MAIL_ADDRESS, mailAddress);

			return entity;
		}

		return null;

	}

}
