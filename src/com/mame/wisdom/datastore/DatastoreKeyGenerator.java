package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mame.wisdom.util.DbgUtil;

public class DatastoreKeyGenerator {

	private final static String TAG = DatastoreKeyGenerator.class
			.getSimpleName();

	public static Key getAllUserDataKey() {
		DbgUtil.showLog(TAG, "getAllUserDataKey");
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_USER,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	public static Key getUserDataKey(long userId) {
		DbgUtil.showLog(TAG, "getUserDataKey");
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_DATA,
				userId);
		return key;
	}

	// TODO we have to update DBConstant.ENTITY_TOTAL_USER_NUM part.
	public static Key getSubCategoryKey() {
		DbgUtil.showLog(TAG, "getSubCategoryKey");
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_SUB_CATEGORY,
				DBConstant.ENTITY_TOTAL_USER_NUM);
		return ancKey;
	}

	public static Key getWisdomKey(long wisdomId) {
		DbgUtil.showLog(TAG, "getWisdomKey");
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_SUB_CATEGORY,
				wisdomId);
		return key;
	}

}
