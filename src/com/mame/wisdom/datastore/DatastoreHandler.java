package com.mame.wisdom.datastore;

import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.util.DbgUtil;

public class DatastoreHandler {

	private final static String TAG = "DatastoreHandler";

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	private static Key getAllUserDataKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_USER,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	private static Key getUserDataKey(long userId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_DATA,
				userId);
		return key;
	}

	// TODO we have to update DBConstant.ENTITY_TOTAL_USER_NUM part.
	private static Key getSubCategoryKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_SUB_CATEGORY,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	private static Key getWisdomKey(long wisdomId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_SUB_CATEGORY,
				wisdomId);
		return key;
	}

	public synchronized void storeUserData(WDUserData data) {
		DbgUtil.showLog(TAG, "storeUserData");

		long userId = data.getUserId();
		Key userKey = getUserDataKey(userId);

		// Filter nameFilter = new FilterPredicate(LcomConst.ENTITY_USER_NAME,
		// FilterOperator.EQUAL, userNameInt);
		//
		// Query query = new Query(LcomUserData.class.getSimpleName());
		// query.setKeysOnly();
		// query.setFilter(nameFilter);
		// PreparedQuery pQuery = ds.prepare(query);
		// Entity entity = pQuery.asSingleEntity();

		Key ancKey = DatastoreHandler.getAllUserDataKey();
		Entity entity = null;
		try {
			entity = mDS.get(ancKey);
		} catch (EntityNotFoundException e) {
			// if no All user data exist, create it as total user num is 0.
			// (Because we will add +1 below part)
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		Query query = new Query(WDUserData.class.getSimpleName());
		query.setKeysOnly();
		PreparedQuery pQuery = mDS.prepare(query);
		Entity alluserEntity = pQuery.asSingleEntity();

		// If alluser Entity exists
		if (alluserEntity != null) {
			// Plus the number of total user num

			// Add new user
		} else {
			// If alluser entity doesn't exist
		}

		// // If user is already exist
		// if (entity == null) {
		// DbgUtil.showLog(TAG, "entity null");
		//
		// // Start transaction
		// Transaction tx = mDS.beginTransaction();
		//
		// try {
		// // Try to get entity itself
		// Key ancKey = getAllUserDataKey();
		// Key key = KeyFactory.createKey(ancKey,
		// DBConstant.KIND_USER_DATA, userId);
		//
		// Entity newEntity =
		//
		// mDS.put(entity);
		//
		// tx.commit();
		// } catch (ConcurrentModificationException e) {
		// DbgUtil.showLog(TAG,
		// "ConcurrentModificationException: " + e.getMessage());
		// if (tx.isActive()) {
		// tx.rollback();
		// }
		// }
		// } else {
		// DbgUtil.showLog(TAG, "entity is not null");
		// // If user already exist
		// // Illegal case, nothing to do
		// // TODO
		// }
	}
}
