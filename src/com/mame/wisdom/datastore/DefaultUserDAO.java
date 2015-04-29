package com.mame.wisdom.datastore;

import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDAllUserData;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.memcache.UserRankingMemcacheService;
import com.mame.wisdom.datastore.memcache.WDMemcacheManager;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DefaultUserDAO implements UserDAO {

	private final static String TAG = DefaultUserDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public void createAllUserDataIfNecessary() {
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
		DbgUtil.showLog(TAG, "findUserDataByTwitterAccount");

		if (twitterName == null) {
			throw new WisdomDatastoreException(
					"Illegal argument. twitterName is null");
		}

		// Twitter name filter
		Filter twitterFilter = new FilterPredicate(
				DBConstant.ENTITY_USER_TWITTER_NAME, FilterOperator.EQUAL,
				twitterName);

		try {
			Key key = DatastoreKeyGenerator.getAllUserDataKey();
			Query query = new Query(DBConstant.KIND_USER_DATA, key);
			query.setFilter(twitterFilter);
			PreparedQuery pQuery = mDS.prepare(query);
			Entity entity = pQuery.asSingleEntity();
			if (entity != null) {
				DefaultUserDAOHelper helper = new DefaultUserDAOHelper();
				return helper.constructUserDataFromEntity(entity);
			}
		} catch (TooManyResultsException e1) {
			DbgUtil.showLog(TAG, "TooManyResultsException: " + e1.getMessage());
		} catch (IllegalStateException e2) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e2.getMessage());
		}

		return null;

	}

	/**
	 * If userId is -1 (No user), it means we haven't assign user ID to him/her
	 * yet.
	 */
	@Override
	public synchronized long storeNewUserData(WDUserData data)
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

			// Return new User id
			return newUserNum;

		} catch (ConcurrentModificationException e) {
			DbgUtil.showLog(TAG, "ConcurrentModificationException");
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new WisdomDatastoreException(e.getMessage());
		}
	}

	private void addNewUserData(WDUserData data) {
		DbgUtil.showLog(TAG, "addNewUserData");

		Entity entity = DefaultUserDAOHelper.createEntityFromUserData(data);

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

	@Override
	public void updateUserData(WDUserData data) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "updateUserData");

		if (data == null) {
			throw new WisdomDatastoreException("Parameter user data is null");
		}

		long userId = data.getUserId();

		if (userId == WConstant.NO_USER) {
			throw new WisdomDatastoreException("Illegal user Id");
		}

		Key key = DatastoreKeyGenerator.getUserDataKey(userId);
		try {
			Entity entity = mDS.get(key);

			// TODO Need to support other fields if we add new one
			if (data.getFacebookName() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_FACEBOOK_NAME,
						data.getFacebookName());
			}

			if (data.getPassword() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_PASSWORD,
						data.getPassword());
			}

			if (data.getThumbnail() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_THUMBNAIL,
						data.getThumbnail());
			}

			if (data.getTotalPoint() != 0) {
				entity.setProperty(DBConstant.ENTITY_USER_TOTAL_POINT,
						data.getTotalPoint());
			}

			if (data.getTwitterName() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_TWITTER_NAME,
						data.getTwitterName());
			}

			if (data.getTwitterToken() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN,
						data.getTwitterName());
			}

			if (data.getTwitterTokenSecret() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_TWITTER_TOKEN_SECRET,
						data.getTwitterName());
			}

			if (data.getUsername() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_NAME,
						data.getUsername());
			}

			if (data.getMailAddress() != null) {
				entity.setProperty(DBConstant.ENTITY_USER_MAIL_ADDRESS,
						data.getMailAddress());
			}

			mDS.put(entity);

		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

	}

	@Override
	public WDUserData getUserData(long userId) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserData");
		if (userId == WConstant.NO_USER) {
			throw new WisdomDatastoreException("illegal userid parameter.");
		}

		Key userKey = DatastoreKeyGenerator.getUserDataKey(userId);

		if (userKey != null) {
			try {
				Entity entity = mDS.get(userKey);
				DefaultUserDAOHelper helper = new DefaultUserDAOHelper();
				return helper.constructUserDataFromEntity(entity);
			} catch (EntityNotFoundException e) {
				DbgUtil.showLog(TAG,
						"EntityNotFoundException: " + e.getMessage());
				throw new WisdomDatastoreException(e.getMessage());
			}
		}

		return null;
	}

	@Override
	public List<WDUserData> getHighestPointUserList(int limit)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getHighestPointUserList");

		try {

			WDMemcacheManager memManager = new WDMemcacheManager(
					new UserRankingMemcacheService());
			List<WDUserData> result = (List<WDUserData>) memManager.getCache();

			if (result == null) {
				DbgUtil.showLog(TAG, "userranking memcache doesn't exist");
				Filter pointFilter = new FilterPredicate(
						DBConstant.ENTITY_USER_TOTAL_POINT,
						FilterOperator.NOT_EQUAL, 0);

				Key key = DatastoreKeyGenerator.getAllUserDataKey();
				Query query = new Query(DBConstant.KIND_USER_DATA, key)
						.addSort(DBConstant.ENTITY_USER_TOTAL_POINT,
								SortDirection.DESCENDING);
				query.setFilter(pointFilter);
				PreparedQuery pQuery = mDS.prepare(query);
				FetchOptions fetch = FetchOptions.Builder.withOffset(0).limit(
						limit);
				List<Entity> entities = pQuery.asList(fetch);

				if (entities != null) {
					DbgUtil.showLog(TAG, "size: " + entities.size());
					DefaultUserDAOHelper helper = new DefaultUserDAOHelper();
					result = helper.parseEntityListToUserDataList(entities);

					if (result != null) {
						memManager.setCache(result);
					}

					return result;

				}
			} else {
				DbgUtil.showLog(TAG, "userranking memcache already exist");
				return result;
			}

		} catch (IllegalArgumentException e) {
			DbgUtil.showLog(TAG, "IllegalArgumentException: " + e.getMessage());
		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		}

		return null;
	}

	@Override
	public WDUserData getUserIdByUserName(String userName)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserIdByUserName");

		if (userName == null) {
			throw new IllegalArgumentException("userName is null");
		}

		Filter userNameFilter = new FilterPredicate(
				DBConstant.ENTITY_USER_NAME, FilterOperator.EQUAL, userName);

		try {
			Key key = DatastoreKeyGenerator.getAllUserDataKey();
			Query query = new Query(DBConstant.KIND_USER_DATA, key);
			query.setFilter(userNameFilter);
			PreparedQuery pQuery = mDS.prepare(query);
			Entity entity = pQuery.asSingleEntity();
			if (entity != null) {
				DbgUtil.showLog(TAG, "Entity is not null");
				DefaultUserDAOHelper helper = new DefaultUserDAOHelper();
				return helper.constructUserDataFromEntity(entity);
			}
		} catch (TooManyResultsException e) {
			DbgUtil.showLog(TAG, "TooManyResultsException: " + e.getMessage());
		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		}

		return null;
	}

	@Override
	public long updateUserPoint(long userId, long updatePoint)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "updateUserPoint: " + userId);

		if (userId == WConstant.NO_USER || updatePoint < 0) {
			throw new IllegalArgumentException("Illegal parameter");
		}

		Key key = DatastoreKeyGenerator.getUserDataKey(userId);
		try {
			Entity entity = mDS.get(key);
			DefaultUserDAOHelper helper = new DefaultUserDAOHelper();
			WDUserData data = helper.constructUserDataFromEntity(entity);
			long totalPoint = data.getTotalPoint();
			long newPoint = totalPoint + updatePoint;
			DbgUtil.showLog(TAG, "newPoint: " + newPoint);
			data.setTotalPoint(newPoint);

			Entity newEntity = DefaultUserDAOHelper
					.createEntityFromUserData(data);
			if (newEntity != null) {
				mDS.put(newEntity);
			} else {
				DbgUtil.showLog(TAG, "newEntity is null");
			}

			return newPoint;

		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		return updatePoint;
	}
}
