package com.mame.wisdom.datastore;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.TimeUtil;

public class UserDataFacade {

	private final static String TAG = UserDataFacade.class.getSimpleName();

	public UserDataFacade() {
		DbgUtil.showLog(TAG, "UserDataFacade");
	}

	public boolean createNewUserData(WDUserData data)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "createNewUserData");

		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();

		// TODO
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

	public void saveTwitterTokens(String twitterName, String profileImageURL,
			String token, String tokenSecret) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "saveTwitterTokens");

		if (twitterName == null || token == null || tokenSecret == null) {
			throw new WisdomDatastoreException("token or tokenSecret is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();

		long current = TimeUtil.getCurrentDate();

		WDUserData data = new WDUserData(WConstant.NO_USER, twitterName, token,
				tokenSecret, null, null, null, profileImageURL, current, 0);
		userDAO.storeNewUserData(data);

	}

}
