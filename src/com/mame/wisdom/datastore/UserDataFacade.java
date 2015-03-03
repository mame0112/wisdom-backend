package com.mame.wisdom.datastore;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

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

}
