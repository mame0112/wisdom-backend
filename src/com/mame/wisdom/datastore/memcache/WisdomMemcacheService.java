package com.mame.wisdom.datastore.memcache;

import java.util.List;
import java.util.logging.Level;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.MemcacheException;
import com.mame.wisdom.util.DbgUtil;

public class WisdomMemcacheService implements WDMemcacheService {

	private final static String TAG = WisdomMemcacheService.class
			.getSimpleName();

	public WisdomMemcacheService() {
	}

	@Override
	public void setCache(Object param) throws MemcacheException {
		DbgUtil.showLog(TAG, "setCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			if (param == null) {
				throw new MemcacheException("param is null");
			}

			if (!(param instanceof List<?>)) {
				throw new MemcacheException("Illegal param type");
			}

			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));
			syncCache.put(MemcacheConstant.LATEST_WISDOMS, param);
		}

	}

	@Override
	public Object getCache() throws MemcacheException {
		DbgUtil.showLog(TAG, "getCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));

			List<WDWisdomData> data = (List<WDWisdomData>) syncCache
					.get(MemcacheConstant.LATEST_WISDOMS);

			return data;
		}
		return null;

	}

	@Override
	public void clearCache() throws MemcacheException {
		DbgUtil.showLog(TAG, "clearCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));
			syncCache.clearAll();
		}

	}
}
