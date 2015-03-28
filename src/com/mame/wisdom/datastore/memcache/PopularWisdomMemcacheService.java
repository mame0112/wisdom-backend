package com.mame.wisdom.datastore.memcache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.MemcacheException;
import com.mame.wisdom.util.DbgUtil;

public class PopularWisdomMemcacheService implements WDMemcacheService {

	private final static String TAG = PopularWisdomMemcacheService.class
			.getSimpleName();

	public PopularWisdomMemcacheService() {
		DbgUtil.showLog(TAG, "PopularWisdomMemcacheService");
	}

	@Override
	public void setCache(Object param) throws MemcacheException {
		DbgUtil.showLog(TAG, "Popular wisdom setCache");

		DbgUtil util = new DbgUtil();
		util.showTime("Popular wisdom setCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			if (param == null) {
				throw new MemcacheException("param is null");
			}

			if (!(param instanceof List<?>)) {
				throw new MemcacheException("Illegal param type");
			}

			List<WDWisdomData> data = (List<WDWisdomData>) param;

			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));
			try {
				syncCache.put(MemcacheConstant.POPULAR_WISDOMS,
						convertWisdomListToByteArray(data));
			} catch (IllegalArgumentException e) {
				DbgUtil.showLog(TAG,
						"IllegalArgumentException: " + e.getMessage());
			} catch (MemcacheServiceException e) {
				DbgUtil.showLog(TAG,
						"MemcacheServiceException: " + e.getMessage());
			}

		}

		util.showTime("Popular wisdom setCache");

	}

	@Override
	public Object getCache() throws MemcacheException {
		DbgUtil.showLog(TAG, "Popular wisdom getCache");

		DbgUtil util = new DbgUtil();
		util.showTime("Popular wisdom getCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));

			// List<WDWisdomData> data = (List<WDWisdomData>) syncCache
			// .get(MemcacheConstant.LATEST_WISDOMS);
			byte[] param = (byte[]) syncCache
					.get(MemcacheConstant.LATEST_WISDOMS);
			List<WDWisdomData> result = convertByteArrayToWisdomList(param);

			util.showTime("Popular wisdom getCache");

			return result;
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

	private List<WDWisdomData> convertByteArrayToWisdomList(byte[] input) {
		DbgUtil.showLog(TAG, "convertByteArrayToWisdomList");
		if (input != null) {
			List<WDWisdomData> result = null;
			try {
				ByteArrayInputStream byteis = new ByteArrayInputStream(input);
				ObjectInputStream objis = new ObjectInputStream(byteis);
				result = (List<WDWisdomData>) objis.readObject();
				byteis.close();
				objis.close();
				return result;
			} catch (IOException e) {
				DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				DbgUtil.showLog(TAG,
						"ClassNotFoundException: " + e.getMessage());
			}

		}
		return null;
	}

	private byte[] convertWisdomListToByteArray(List<WDWisdomData> data) {

		byte[] retObject = null;
		try {
			ByteArrayOutputStream byteos = new ByteArrayOutputStream();
			ObjectOutputStream objos = new ObjectOutputStream(byteos);
			objos.writeObject(data);
			objos.close();
			byteos.close();
			retObject = byteos.toByteArray();
			return retObject;
		} catch (IOException e) {
			DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
		}
		return null;
	}

}
