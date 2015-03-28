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
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.MemcacheException;
import com.mame.wisdom.util.DbgUtil;

public class UserRankingMemcacheService implements WDMemcacheService {

	private final static String TAG = UserRankingMemcacheService.class
			.getSimpleName();

	public UserRankingMemcacheService() {
		DbgUtil.showLog(TAG, "UserRankingMemcacheService");
	}

	@Override
	public void setCache(Object param) throws MemcacheException {
		DbgUtil.showLog(TAG, "userranking setCache");

		DbgUtil util = new DbgUtil();
		util.showTime("userranking setCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			if (param == null) {
				throw new MemcacheException("param is null");
			}

			if (!(param instanceof List<?>)) {
				throw new MemcacheException("Illegal param type");
			}

			List<WDUserData> data = (List<WDUserData>) param;

			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));
			try {
				syncCache.put(MemcacheConstant.LATEST_WISDOMS,
						convertUserListToByteArray(data));
			} catch (IllegalArgumentException e) {
				DbgUtil.showLog(TAG,
						"IllegalArgumentException: " + e.getMessage());
			} catch (MemcacheServiceException e) {
				DbgUtil.showLog(TAG,
						"MemcacheServiceException: " + e.getMessage());
			}

		}

		util.showTime("userranking setCache");

	}

	@Override
	public Object getCache() throws MemcacheException {
		DbgUtil.showLog(TAG, "userranking getCache");

		DbgUtil util = new DbgUtil();
		util.showTime("userranking getCache");

		if (MemcacheConstant.USE_MEMCACHE) {
			MemcacheService syncCache = MemcacheServiceFactory
					.getMemcacheService();
			syncCache.setErrorHandler(ErrorHandlers
					.getConsistentLogAndContinue(Level.INFO));

			byte[] param = (byte[]) syncCache.get(MemcacheConstant.USER_RAKING);
			List<WDUserData> result = convertByteArrayToUserList(param);

			util.showTime("userranking getCache");

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

	private List<WDUserData> convertByteArrayToUserList(byte[] input) {
		if (input != null) {
			List<WDUserData> result = null;
			try {
				ByteArrayInputStream byteis = new ByteArrayInputStream(input);
				ObjectInputStream objis = new ObjectInputStream(byteis);

				result = (List<WDUserData>) objis.readObject();

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

	private byte[] convertUserListToByteArray(List<WDUserData> data) {

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
