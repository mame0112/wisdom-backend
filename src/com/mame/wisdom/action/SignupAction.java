package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.DefaultUserDAO;
import com.mame.wisdom.datastore.UserDAO;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.jsonbuilder.JsonBuilder;
import com.mame.wisdom.jsonbuilder.SignupJsonBuilder;
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
		String twitterToken = request
				.getParameter(WConstant.SERVLET_TWITTER_TOKEN);
		String twitterTokenSecret = request
				.getParameter(WConstant.SERVLET_TWITTER_TOKEN_SECRET);
		String facebookName = request
				.getParameter(WConstant.SERVLET_FACEBOOK_NAME);
		String userName = request.getParameter(WConstant.SERVLET_USER_NAME);
		String password = request.getParameter(WConstant.SERVLET_PASSWORD_NAME);
		String thubmnail = request
				.getParameter(WConstant.SERVLET_THUMBNAIL_URL);
		// Blob thumbBlob = DatastoreUtil.transcodeString2Blob(thubmnail);

		String result = null;
		JsonBuilder builder = new SignupJsonBuilder();
		if (responseId != null) {
			builder.addResponseId(Integer.valueOf(responseId));

			long currentTime = TimeUtil.getCurrentDate();
			WDUserData data = new WDUserData(WConstant.NO_USER, twitterName,
					twitterToken, twitterTokenSecret, facebookName, userName,
					password, thubmnail, currentTime, 0, null);

			UserDAO userDAO = new DefaultUserDAO();
			try {
				userDAO.storeNewUserData(data);
			} catch (WisdomDatastoreException e) {
				builder.addErrorMessage(e.getMessage());
			}

		} else {
			builder.addErrorMessage("response id is null");
		}

		result = builder.getResultJson();

		return result;
	}
}
