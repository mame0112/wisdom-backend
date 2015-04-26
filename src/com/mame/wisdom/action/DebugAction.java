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
		for (int i = 1; i < 15; i++) {
			try {

				if (userDatafacade.getUserData(i) == null) {
					WDUserData data = new WDUserData(WConstant.NO_USER,
							"Test twitter name" + i, "twitter token" + i,
							"twitterTokenSecret" + i, "facebookName" + i,
							"userName" + i, "password" + i, null,
							TimeUtil.getCurrentDate(), i * 10, null);
					userDatafacade.createNewUserData(data);

				}

			} catch (Exception e) {
				WDUserData data = new WDUserData(WConstant.NO_USER,
						"Test twitter name" + i, "twitter token" + i,
						"twitterTokenSecret" + i, "facebookName" + i,
						"userName" + i, "password" + i, null,
						TimeUtil.getCurrentDate(), i * 10, null);
				userDatafacade.createNewUserData(data);
			}
		}
		// if (userDatafacade.getUserData(2) == null) {
		// WDUserData data = new WDUserData(WConstant.NO_USER,
		// "Test twitter name2", "twitter token2",
		// "twitterTokenSecret2", "facebookName2", "userName2",
		// "password2", null, TimeUtil.getCurrentDate(), 20);
		// userDatafacade.createNewUserData(data);
		//
		// }

		return null;
	}

}
