package com.mame.wisdom.datastore.memcache;

import com.mame.wisdom.exception.MemcacheException;
import com.mame.wisdom.util.DbgUtil;

public class WDMemcacheManager {

	private final static String TAG = WDMemcacheManager.class.getSimpleName();

	private WDMemcacheService mService = null;

	//	private final static WDMemcacheManager mManager = new WDMemcacheManager();

	public WDMemcacheManager(WDMemcacheService strategry) {
		mService = strategry;
	}

	// public static WDMemcacheManager getInstance(WDMemcacheService strategry)
	// {
	// mService = strategry;
	// return mManager;
	// }

	public void setCache(Object param) {
		DbgUtil.showLog(TAG, "setCache");
		try {
			mService.setCache(param);
		} catch (MemcacheException e) {
			DbgUtil.showLog(TAG, "MemcacheException: " + e.getMessage());
		}
	}

	public Object getCache() {
		DbgUtil.showLog(TAG, "getCache");
		try {
			return mService.getCache();
		} catch (MemcacheException e) {
			DbgUtil.showLog(TAG, "MemcacheException: " + e.getMessage());
		}
		return null;

	}

}
