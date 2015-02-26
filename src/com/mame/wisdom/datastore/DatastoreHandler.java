package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DatastoreHandler {

	private final static String TAG = "DatastoreHandler";

	public static Key getAllUserDataKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_USER,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	public static Key getUserDataKey(long userId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_DATA,
				userId);
		return key;
	}

	// TODO we have to update DBConstant.ENTITY_TOTAL_USER_NUM part.
	public static Key getSubCategoryKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_SUB_CATEGORY,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	public static Key getWisdomKey(long wisdomId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_SUB_CATEGORY,
				wisdomId);
		return key;
	}
}
