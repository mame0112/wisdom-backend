package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserStatusData;
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

	public List<WDUserStatusData> parseEntityListToUserStatusDataList(
			List<Entity> entities) {
		DbgUtil.showLog(TAG, "parseEntityListToUserStatusDataList");

		if (entities != null) {
			List<WDUserStatusData> result = new ArrayList<WDUserStatusData>();
			for (Entity e : entities) {
				result.add(constructUserStatusDataFromEntity(e));
			}
			return result;
		}

		return null;
	}

	public static WDUserData constructUserDataFromEntity(Entity entity) {
		DbgUtil.showLog(TAG, "constructUserDataFromEntity");

		if (entity != null) {
			try {
				long userId = (Long) entity
						.getProperty(DBConstant.ENTITY_USER_ID);
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
				String mailAddress = (String) entity
						.getProperty(DBConstant.ENTITY_USER_MAIL_ADDRESS);
				WDUserData data = new WDUserData(userId, twitterName,
						twitterToken, twitterTokenSecret, facebookName,
						userName, password, thumbnail, mailAddress, lastLogin);
				return data;
			} catch (Exception e) {
				DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
			}

		} else {
			DbgUtil.showLog(TAG, "Entity is null");
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public WDUserStatusData constructUserStatusDataFromEntity(Entity entity) {
		DbgUtil.showLog(TAG, "constructUserStatusDataFromEntity");

		if (entity != null) {
			long userId = entity.getKey().getId();
			long totalPoint = (Long) entity
					.getProperty(DBConstant.ENTITY_STATUS_TOTAL_POINT);
			List<Long> createdWisdoms = (List<Long>) entity
					.getProperty(DBConstant.ENTITY_STATUS_CREATED_WISDOM);
			List<Long> likedWisdoms = (List<Long>) entity
					.getProperty(DBConstant.ENTITY_STATUS_LIKED_WISDOM);
			return new WDUserStatusData(userId, totalPoint, createdWisdoms,
					likedWisdoms);
		}
		return null;
	}

	public static Entity createEntityFromUserStatusData(WDUserStatusData data) {
		DbgUtil.showLog(TAG, "createEntityFromUserStatusData");
		if (data != null) {
			Key ancKey = DatastoreKeyGenerator.getAllUserDataKey();
			Entity entity = new Entity(DBConstant.KIND_USER_STATUS,
					data.getUserId(), ancKey);

			long totalPoint = data.getTotalPoint();
			List<Long> createdWisdoms = data.getCreatedWisdomIds();
			List<Long> likedWisdoms = data.getLikedWisdomIds();

			entity.setProperty(DBConstant.ENTITY_STATUS_TOTAL_POINT, totalPoint);
			entity.setProperty(DBConstant.ENTITY_STATUS_CREATED_WISDOM,
					createdWisdoms);
			entity.setProperty(DBConstant.ENTITY_STATUS_LIKED_WISDOM,
					likedWisdoms);

			return entity;
		}
		return null;
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
			entity.setProperty(DBConstant.ENTITY_USER_LAST_LOGIN, lastLogin);
			entity.setProperty(DBConstant.ENTITY_USER_MAIL_ADDRESS, mailAddress);

			return entity;
		}

		return null;

	}

}
