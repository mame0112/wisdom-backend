package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.DefaultUserDAO;
import com.mame.wisdom.datastore.UserDAO;
import com.mame.wisdom.util.DatastoreUtil;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.TimeUtil;

public class SignupAction implements Action {

	private final static String TAG = SignupAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "SignupAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);
		String twitterName = request
				.getParameter(WConstant.SERVLET_TWITTER_NAME);
		String facebookName = request
				.getParameter(WConstant.SERVLET_FACEBOOK_NAME);
		String userName = request.getParameter(WConstant.SERVLET_USER_NAME);
		String password = request.getParameter(WConstant.SERVLET_PASSWORD_NAME);
		String thubmnail = request.getParameter(WConstant.SERVLET_THUMBNAIL);
		Blob thumbBlob = DatastoreUtil.transcodeString2Blob(thubmnail);

		long currentTime = TimeUtil.getCurrentDate();

		WDUserData data = new WDUserData(WConstant.NO_USER, twitterName,
				facebookName, userName, password, thumbBlob, currentTime, 0);

		UserDAO userDAO = new DefaultUserDAO();
		// TODO
		userDAO.storeNewUserData(data);

		return null;
	}
}
