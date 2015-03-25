package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.TimeUtil;

public class DebugAction implements Action {

	private final static String TAG = DebugAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "DebugAction execute");

		// Create dummy user data
		UserDataFacade userDatafacade = new UserDataFacade();

		if (userDatafacade.getUserData(1) == null) {
			WDUserData data = new WDUserData(WConstant.NO_USER,
					"Test twitter name1", "twitter token",
					"twitterTokenSecret", "facebookName", "userName",
					"password", null, TimeUtil.getCurrentDate(), 10);
			userDatafacade.createNewUserData(data);

		}

		return null;
	}

}
