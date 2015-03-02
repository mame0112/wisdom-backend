package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
	public long getCurrentTotalUserNumber() throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getCurrentTotalUserNumber");

		Key key = DatastoreKeyGenerator.getAllUserDataKey();
		Query query = new Query(WDAllUserData.class.getSimpleName(), key);
		PreparedQuery pQuery = mDS.prepare(query);
		Entity entity = pQuery.asSingleEntity();

		long numOfUser = (Long) entity
				.getProperty(DBConstant.ENTITY_TOTAL_USER_NUM);

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

	// TODO Need to check sequence if we can use userId that is stored on the
	// WDUser data in argument.
	@Override
	public void storeNewUserData(WDUserData data)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "storeNewUserData");

		if (data == null) {
			throw new WisdomDatastoreException(
					"Illegal argument. WDUserData is null");
		}

		long userId = data.getUserId();

		if (userId == WConstant.NO_USER) {
			throw new WisdomDatastoreException(
					"Illegal argument. UserId is no user");
		}

		// If all user entity exist
		if (getCurrentTotalUserNumber() != WConstant.NO_USER) {

		} else {
			// If all user entity doesn't exist

			// Create all user Entity with total user number if 1
			createTotalUserData();

			// store target user data
			addNewUserData(data);
		}

	}

	private void createTotalUserData() {
		DbgUtil.showLog(TAG, "createTotalUserData");

		Key key = DatastoreKeyGenerator.getAllUserDataKey();
		Entity entity = new Entity(key);
		entity.setProperty(DBConstant.ENTITY_TOTAL_USER_NUM, (long) 1);
		mDS.put(entity);

	}

	private void addNewUserData(WDUserData data) {
		DbgUtil.showLog(TAG, "addNewUserData");
		long userId = data.getUserId();

		Key ancKey = DatastoreKeyGenerator.getAllUserDataKey();
		Entity entity = new Entity(DBConstant.KIND_USER_DATA, ancKey);

		entity.setProperty(DBConstant.ENTITY_USER_ID, userId);
		entity.setProperty(DBConstant.ENTITY_USER_TWITTER_NAME,
				data.getTwitterName());

		mDS.put(entity);

	}

	private WDUserData constructUserDataFromEntity(Entity entity) {
		DbgUtil.showLog(TAG, "constructUserDataFromEntity");

		long userId = (Long) entity.getProperty(DBConstant.ENTITY_USER_ID);
		String twitterName = (String) entity
				.getProperty(DBConstant.ENTITY_USER_TWITTER_NAME);

		WDUserData data = new WDUserData(userId, twitterName);

		return data;
	}

}
