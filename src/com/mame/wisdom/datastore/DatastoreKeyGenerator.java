package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DatastoreKeyGenerator {

	private final static String TAG = DatastoreKeyGenerator.class
			.getSimpleName();

	public static Key getAllUserDataKey() {
		DbgUtil.showLog(TAG, "getAllUserDataKey");
		Key ancKey = KeyFactory.createKey(DBConstant.KIND_ALL_USER,
				DBConstant.ENTITY_TOTAL_USER_NUMBER);
		return ancKey;
	}

	public static Key getUserDataKey(long userId) {
		DbgUtil.showLog(TAG, "getUserDataKey");
		Key ancKey = getAllUserDataKey();
		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_USER_DATA,
				userId);
		return key;
	}

	public static Key getSubCategoryKey(String category, String subCategory)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getSubCategoryKey");

		if (category == null || subCategory == null) {
			throw new WisdomDatastoreException(
					"category or subCategory identifier is null");
		}

		String identifier = category + "/" + subCategory;

		Key ancKey = KeyFactory.createKey(DBConstant.KIND_SUB_CATEGORY,
				identifier);
		return ancKey;
	}

	public static Key getWisdomKeyById(String category, String subCategory,
			long wisdomId) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdomKey");
		Key ancKey = getSubCategoryKey(category, subCategory);
		Key key = KeyFactory
				.createKey(ancKey, DBConstant.KIND_WISDOM, wisdomId);
		return key;
	}

//	public static Key getWAllCategoryKey() {
//		DbgUtil.showLog(TAG, "getSubcategoryKeyForAll");
//		Key key = KeyFactory.createkey
//		Key ancKey = getSubCategoryKey(category, subCategory);		
//		Key key = KeyFactory.createKey(ancKey, DBConstant.KIND_WISDOM);
	//	}
}
