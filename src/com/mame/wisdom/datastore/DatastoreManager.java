package com.mame.wisdom.datastore;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DatastoreManager {

	private final static String TAG = DatastoreManager.class.getSimpleName();

	private static DatastoreManager sManager = new DatastoreManager();

	private static DatastoreHandler mHandler = new DatastoreHandler();

	// Singletone
	private DatastoreManager() {

	}

	public static DatastoreManager getInstance() {
		return sManager;
	}

	public static synchronized void addNewUserData(WDUserData data)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "addNewUserData");
		if (data == null) {
			throw new WisdomDatastoreException("data is null");
		}

		if (data.getUserId() == WConstant.NO_USER) {
			throw new WisdomDatastoreException("Illegal userId");
		}

		mHandler.storeUserData(data);
	}

	// TODO
	public static synchronized long getUserId() {
		DbgUtil.showLog(TAG, "getUserId");

		return WConstant.NO_USER;
	}

	public static synchronized long getUserData(long userId)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserData");

		if (userId == WConstant.NO_USER) {
			throw new WisdomDatastoreException("Illegal user id");
		}
		
//		mHandler

		return WConstant.NO_USER;
	}

}
