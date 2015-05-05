package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDHighlighPointUserData;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserDataBuilder;
import com.mame.wisdom.data.WDUserStatusData;
import com.mame.wisdom.datastore.memcache.UserRankingMemcacheService;
import com.mame.wisdom.datastore.memcache.WDMemcacheManager;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.exception.WisdomFacadeException;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.TimeUtil;

public class UserDataFacade {

	private final static String TAG = UserDataFacade.class.getSimpleName();

	public UserDataFacade() {
		DbgUtil.showLog(TAG, "UserDataFacade");
	}

	public synchronized boolean createNewUserData(WDUserData data)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "createNewUserData");

		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();

		userDAO.storeNewUserData(data);

		return false;
	}

	public WDUserData getUserDataByTwitterName(String twitterName)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserIdByTwitterName");

		if (twitterName == null) {
			throw new WisdomDatastoreException(
					"Illegal argument. twitterName is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();
		WDUserData data = userDAO.findUserDataByTwitterAccount(twitterName);

		return data;

	}

	public WDUserData saveTwitterTokens(String twitterName,
			String profileImageURL, String token, String tokenSecret)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "saveTwitterTokens");

		if (twitterName == null || token == null || tokenSecret == null) {
			throw new WisdomDatastoreException("token or tokenSecret is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();

		WDUserData currentData = userDAO
				.findUserDataByTwitterAccount(twitterName);
		long userId = WConstant.NO_USER;
		if (currentData == null) {
			DbgUtil.showLog(TAG, "Current data is null");
			long current = TimeUtil.getCurrentDate();

			WDUserData data = WDUserDataBuilder.createFrom(null)
					.setUserId(userId).setTwitterName(twitterName)
					.setTwitterToken(token).setTwitterTokenSecret(tokenSecret)
					.setThumbnail(profileImageURL).setLastLoginDate(current)
					.getConstructedData();

			// WDUserData data = new WDUserData(WConstant.NO_USER, twitterName,
			// token, tokenSecret, null, null, null, profileImageURL,
			// current, 0, null);
			userDAO.storeNewUserData(data);
			return data;
		} else {
			DbgUtil.showLog(TAG,
					"Target twitter account has already been registered!");
			return currentData;
		}
	}

	public WDUserData getUserData(long userId) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getUserData");

		if (userId == WConstant.NO_USER) {
			throw new WisdomFacadeException("Illegal userId, -1");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			UserDAO dao = factory.getUserDAO();
			return dao.getUserData(userId);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}
	}

	public WDUserStatusData getUserStatusData(long userId)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getUserStatusData");

		if (userId == WConstant.NO_USER) {
			throw new WisdomFacadeException("Illegal userId");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			UserDAO dao = factory.getUserDAO();
			return dao.getUserStatusData(userId);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}
	}

	public List<WDHighlighPointUserData> getUserPointRankingList(int limit) {
		DbgUtil.showLog(TAG, "getUserPointRankingList");

		WDMemcacheManager memManager = new WDMemcacheManager(
				new UserRankingMemcacheService());
		List<WDHighlighPointUserData> result = (List<WDHighlighPointUserData>) memManager
				.getCache();

		// If no cache exists
		if (result == null) {
			DAOFactory factory = DAOFactory.getDAOFactory();
			try {
				UserDAO dao = factory.getUserDAO();
				List<WDUserStatusData> status = dao
						.getHighestPointUserList(limit);
				Map<Long, WDUserData> userData = dao.getUserDataList(status);
				result = createHighlightData(status, userData);

				if (result != null) {
					memManager.setCache(result);
				}

				return result;
			} catch (WisdomDatastoreException e) {
				DbgUtil.showLog(TAG,
						"WisdomDatastoreException: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				DbgUtil.showLog(TAG,
						"IllegalArgumentException: " + e.getMessage());
			} catch (IllegalStateException e) {
				DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
			}

		} else {
			DbgUtil.showLog(TAG, "userranking memcache already exist");
			return result;
		}

		return null;
	}

	private List<WDHighlighPointUserData> createHighlightData(
			List<WDUserStatusData> status, Map<Long, WDUserData> userData) {
		DbgUtil.showLog(TAG, "createHighlightData");

		if (status != null && userData != null) {

			List<WDHighlighPointUserData> result = new ArrayList<WDHighlighPointUserData>();

			for (WDUserStatusData data : status) {
				long userId = data.getUserId();
				WDUserData uData = userData.get(userId);

				WDHighlighPointUserData hData = new WDHighlighPointUserData(
						userId, uData.getUsername(), data.getTotalPoint());
				result.add(hData);
			}

			return result;
		}

		return null;
	}

	public WDUserData getUserDataByUserName(String userName)
			throws WisdomFacadeException {

		if (userName == null) {
			throw new WisdomFacadeException("parameter is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();

		try {
			UserDAO dao = factory.getUserDAO();
			return dao.getUserIdByUserName(userName);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Update user point according to given parameter.
	 * 
	 * @param userId
	 * @param updatePoint
	 * @return newly updated user point.
	 */
	// public long updateUserPoint(long userId, int updatePoint) {
	// DbgUtil.showLog(TAG, "updateUserPoint");
	//
	// if (userId == WConstant.NO_USER || updatePoint < 0) {
	// throw new IllegalArgumentException("Illegal parameter");
	// }
	//
	// DAOFactory factory = DAOFactory.getDAOFactory();
	//
	// try {
	// UserDAO dao = factory.getUserDAO();
	// return dao.updateUserPoint(userId, updatePoint);
	//
	// } catch (WisdomDatastoreException e) {
	// DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
	// }
	//
	// return updatePoint;
	// }

	public long updateUserStatus(long userId, long updatePoint,
			long createdWisdomId, long likedWisdomId) {
		DbgUtil.showLog(TAG, "updateUserstatus");

		if (userId == WConstant.NO_USER) {
			throw new IllegalArgumentException("Illgal parameter");
		}

		try {
			DAOFactory factory = DAOFactory.getDAOFactory();
			UserDAO dao = factory.getUserDAO();

			return dao.updateUserStatus(userId, updatePoint, createdWisdomId,
					likedWisdomId);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return updatePoint;

	}
}
