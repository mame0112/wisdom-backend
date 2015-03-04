package com.mame.wisdom.datastore;

import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDAllUserData;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class DefaultUserDAO implements UserDAO {

	private final static String TAG = DefaultUserDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	private boolean isAllUserEntityExist() {
		DbgUtil.showLog(TAG, "isAllUserEntityExist");
		Key key = DatastoreKeyGenerator.getAllUserDataKey();

		if (key != null) {
			return true;
		}
		return false;

	}

	@Override
	public void createAllUserDataIfNecessary() throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "createAllUserDataIfNecessary");
		Key key = DatastoreKeyGenerator.getAllUserDataKey();
		try {
			Entity entity = mDS.get(key);
			long num = (Long) entity
					.getProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER);
			DbgUtil.showLog(TAG, "total user Number: " + num);
			if (num == -1) {
				entity.setProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER, 0);
				mDS.put(entity);
			}
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			Entity entity = new Entity(key);
			entity.setProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER, 0);
			mDS.put(entity);
		}

	}

	@Override
	public long getCurrentTotalUserNumber() throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getCurrentTotalUserNumber");

		Key key = DatastoreKeyGenerator.getAllUserDataKey();
		Query query = new Query(WDAllUserData.class.getSimpleName(), key);
		PreparedQuery pQuery = mDS.prepare(query);
		Entity entity = pQuery.asSingleEntity();

		long numOfUser = (Long) entity
				.getProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER);

		return numOfUser;
	}

	@Override
	public WDUserData findUserDataByTwitterAccount(String twitterName)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "isTargetUserDataExist");

		if (twitterName == null) {
			throw new WisdomDatastoreException(
					"Illegal argument. twitterName is null");
		}

		// Twitter name filter
		Filter twitterFilter = new FilterPredicate(
				DBConstant.ENTITY_USER_TWITTER_NAME, FilterOperator.EQUAL,
				twitterName);

		Key key = DatastoreKeyGenerator.getAllUserDataKey();
		Query query = new Query(WDAllUserData.class.getSimpleName(), key);
		query.setFilter(twitterFilter);
		PreparedQuery pQuery = mDS.prepare(query);
		Entity entity = pQuery.asSingleEntity();

		return constructUserDataFromEntity(entity);

	}

	/**
	 * If userId is -1 (No user), it means we haven't assign user ID to him/her
	 * yet.
	 */
	@Override
	public synchronized void storeNewUserData(WDUserData data)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "storeNewUserData");

		if (data == null) {
			throw new WisdomDatastoreException(
					"Illegal argument. WDUserData is null");
		}

		long userId = data.getUserId();
		if (userId != WConstant.NO_USER) {
			throw new WisdomDatastoreException(
					"Illegal userId. User Id is not NO_USER, which is -1");
		}

		// Start transaction
		Transaction tx = mDS.beginTransaction();
		try {

			// add the number of user
			long newUserNum = addTheNumberOfUser();

			// Set new userId
			data.setUserId(newUserNum);

			// store target user data
			addNewUserData(data);

			tx.commit();
		} catch (ConcurrentModificationException e) {
			DbgUtil.showLog(TAG, "ConcurrentModificationException");
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}

	private void addNewUserData(WDUserData data) {
		DbgUtil.showLog(TAG, "addNewUserData");
		long userId = data.getUserId();
		String userName = data.getUsername();
		String password = data.getPassword();
		String twitter = data.getTwitterName();
		String facebook = data.getFacebookName();
		Blob thumbnail = data.getThumbnail();
		long lastLogin = data.getLastLoginDate();
		long totalPoint = data.getTotalPoint();

		Key ancKey = DatastoreKeyGenerator.getAllUserDataKey();
		Entity entity = new Entity(DBConstant.KIND_USER_DATA, ancKey);

		entity.setProperty(DBConstant.ENTITY_USER_ID, userId);
		entity.setProperty(DBConstant.ENTITY_USER_NAME, userName);
		entity.setProperty(DBConstant.ENTITY_USER_PASSWORD, password);
		entity.setProperty(DBConstant.ENTITY_USER_TWITTER_NAME, twitter);
		entity.setProperty(DBConstant.ENTITY_USER_FACEBOOK_NAME, facebook);
		entity.setProperty(DBConstant.ENTITY_USER_THUMBNAIL, thumbnail);
		entity.setProperty(DBConstant.ENTITY_USER_TOTAL_POINT, totalPoint);
		entity.setProperty(DBConstant.ENTITY_USER_LAST_LOGIN, lastLogin);

		mDS.put(entity);

	}

	/**
	 * Increase the number of user and return the number of user number.
	 * 
	 * @return
	 */
	private long addTheNumberOfUser() {
		DbgUtil.showLog(TAG, "increaseTheNumberOfUser");

		Key ancKey = DatastoreKeyGenerator.getAllUserDataKey();

		try {
			Entity entity = mDS.get(ancKey);
			long currentNum = (long) entity
					.getProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER);
			long newNum = currentNum + 1;
			entity.setProperty(DBConstant.ENTITY_TOTAL_USER_NUMBER, newNum);
			mDS.put(entity);
			return newNum;
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		return WConstant.NO_USER;

	}

	private WDUserData constructUserDataFromEntity(Entity entity) {
		DbgUtil.showLog(TAG, "constructUserDataFromEntity");

		long userId = (Long) entity.getProperty(DBConstant.ENTITY_USER_ID);
		String twitterName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_TWITTER_NAME);
		String facebookName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_FACEBOOK_NAME);
		String userName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_NAME);
		String password = (String) entity
				.getProperty(DBConstant.ENTITY_USER_PASSWORD);
		Blob thumbnail = (Blob) entity
				.getProperty(DBConstant.ENTITY_USER_THUMBNAIL);
		long lastLogin = (Long) entity
				.getProperty(DBConstant.ENTITY_USER_LAST_LOGIN);
		long totalPoint = (Long) entity
				.getProperty(DBConstant.ENTITY_USER_TOTAL_POINT);

		WDUserData data = new WDUserData(userId, twitterName, facebookName,
				userName, password, thumbnail, lastLogin, totalPoint);

		return data;
	}

	public long getNewUserId() {
		DbgUtil.showLog(TAG, "getNewUserId");

		return WConstant.NO_USER;
	}

}
