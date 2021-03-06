package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DatastoreKeyGenerator {

	private final static String TAG = DatastoreKeyGenerator.class
			.getSimpleName();

	public static Key getAllUserDataKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_USER,
				DBConstant.ENTITY_TOTAL_USER_NUMBER);
		return ancKey;
	}

	public static Key getUserDataKey(long userId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_DATA,
				userId);
		return key;
	}

	public static Key getUserStatusKey(long userId) {
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_STATUS,
				userId);
		return key;
	}

	public static Key getSubCategoryKey(String category, String subCategory)
			throws WisdomDatastoreException {

		if (category == null || subCategory == null) {
			DbgUtil.showLog(TAG, "category or subCategory identifier is null");
			throw new WisdomDatastoreException(
					"category or subCategory identifier is null");
		}

		String identifier = category + "-" + subCategory;

		Key key = KeyFactory
				.createKey(DBConstant.KIND_SUB_CATEGORY, identifier);
		return key;
	}

	public static String getSubCategoryKeyName(String category,
			String subCategory) throws WisdomDatastoreException {

		if (category == null || subCategory == null) {
			DbgUtil.showLog(TAG, "category or subCategory identifier is null");
			throw new WisdomDatastoreException(
					"category or subCategory identifier is null");
		}

		String identifier = category + "-" + subCategory;

		return identifier;
	}

	public static Key getAllWisdomDataKey() {
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_WISDOM,
				DBConstant.ENTITY_TOTAL_WISDOM_NUMBER);
		return ancKey;
	}

	public static Key getWisdomKeyById(long wisdomId)
			throws WisdomDatastoreException {
		Key ancKey = getAllWisdomDataKey();
		Key key = KeyFactory
				.createKey(ancKey, DBConstant.KIND_WISDOM, wisdomId);
		return key;
	}

	// public static Key getWisdomKeyById(String category, String subCategory,
	// long wisdomId) throws WisdomDatastoreException {
	// Key ancKey = getSubCategoryKey(category, subCategory);
	// Key key = KeyFactory
	// .createKey(ancKey, DBConstant.KIND_WISDOM, wisdomId);
	// return key;
	// }
}
