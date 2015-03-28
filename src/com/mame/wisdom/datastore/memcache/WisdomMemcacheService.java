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

public class WisdomMemcacheService implements WDMemcacheService {

	private final static String TAG = WisdomMemcacheService.class
			.getSimpleName();

	public WisdomMemcacheService() {
		DbgUtil.showLog(TAG, "WisdomMemcacheService");
	}

	@Override
	public void setCache(Object param) throws MemcacheException {
		DbgUtil.showLog(TAG, "setCache");

		DbgUtil util = new DbgUtil();
		util.showTime();

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
				syncCache.put(MemcacheConstant.LATEST_WISDOMS,
						convertWisdomListToByteArray(data));
			} catch (IllegalArgumentException e) {
				DbgUtil.showLog(TAG,
						"IllegalArgumentException: " + e.getMessage());
			} catch (MemcacheServiceException e) {
				DbgUtil.showLog(TAG,
						"MemcacheServiceException: " + e.getMessage());
			}

		}

		util.showTime();

	}

	@Override
	public Object getCache() throws MemcacheException {
		DbgUtil.showLog(TAG, "getCache");

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
				DbgUtil.showLog(TAG, "A");
				result = (List<WDWisdomData>) objis.readObject();
				DbgUtil.showLog(TAG, "B");
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

	// private JSONArray parseWisdomDataToJSON(List<WDWisdomData> wisdoms) {
	// DbgUtil.showLog(TAG, "parseWisdomDataToJSON");
	//
	// JSONArray rootArray = new JSONArray();
	//
	// for (WDWisdomData data : wisdoms) {
	// JSONObject obj = new JSONObject();
	//
	// try {
	// obj.put(JsonConstant.PARAM_WISDOM_CREATE_USER_ID,
	// data.getCreatedUserId());
	// obj.put(JsonConstant.PARAM_WISDOM_UPDATED_DATE,
	// data.getLastUpdatedDate());
	// obj.put(JsonConstant.PARAM_WISDOM_ID, data.getWisdomId());
	// obj.put(JsonConstant.PARAM_WISDOM_DESCRIPTION,
	// data.getDescription());
	// obj.put(JsonConstant.PARAM_WISDOM_TAG, data.getTag());
	// obj.put(JsonConstant.PARAM_WISDOM_THUMBNAIL,
	// data.getThumbnakl());
	// obj.put(JsonConstant.PARAM_WISDOM_TITLE, data.getTitle());
	//
	// List<WDWisdomItemEntry> items = data.getItems();
	// if (items != null && items.size() != 0) {
	// for (WDWisdomItemEntry item : items) {
	// item.getItem();
	// item.getItemId();
	// item.getLastUpdateDate();
	// item.getLastUpdateUserId();
	// item.getLastUpdateUserName();
	// item.getNumberOfLike();
	// item.getTag();
	// }
	// }
	// // obj.put(JsonConstant.PARAM_WISDOM_ENTRY_NAME, arg1);
	//
	// } catch (JSONException e) {
	// DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
	// }
	// }
	//
	// return null;
	// }
}
