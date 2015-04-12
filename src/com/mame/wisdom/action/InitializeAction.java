package com.mame.wisdom.action;

import com.mame.wisdom.datastore.DefaultUserDAO;
import com.mame.wisdom.datastore.UserDAO;
import com.mame.wisdom.util.DbgUtil;

public class InitializeAction {

	/**
	 * For first time, we have to secure All user data entity exists.
	 */
	public void initializeService() {

		DbgUtil.showLog("initializeService", "initializeService");

		UserDAO userDAO = new DefaultUserDAO();
		userDAO.createAllUserDataIfNecessary();

	}

}
