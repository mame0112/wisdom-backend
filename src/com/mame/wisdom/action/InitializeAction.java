package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.datastore.DefaultUserDAO;
import com.mame.wisdom.datastore.UserDAO;

public class InitializeAction implements Action {

	/**
	 * For first time, we have to secure All user data entity exists.
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserDAO userDAO = new DefaultUserDAO();
		return null;
	}

}
