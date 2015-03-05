package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.User;

import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.jsonbuilder.TwitterSigninBuilder;
import com.mame.wisdom.twitter.TwitterConstant;
import com.mame.wisdom.util.DbgUtil;

public class TwitterSigninAction implements Action {

	private final static String TAG = TwitterSigninAction.class.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "TwitterSigninAction execute");

		String responseId = request.getParameter(WConstant.SERVLET_RESP_ID);

		Twitter twitter = (Twitter) request.getSession().getAttribute(
				TwitterConstant.KEY_TWITTER);
		String result = null;

		TwitterSigninBuilder builder = new TwitterSigninBuilder();
		if (responseId != null) {
			builder.addResponseId(Integer.valueOf(responseId));
			// If Twitter instance is in session
			if (twitter != null) {
				DbgUtil.showLog(TAG, "Twitter is in session");
				User user = twitter.showUser(twitter.getScreenName());

				UserDataFacade facade = new UserDataFacade();
				WDUserData data = facade.getUserDataByTwitterName(user
						.getScreenName());
				if (data != null) {
					DbgUtil.showLog(TAG, "data is not null");
					long userId = data.getUserId();
					DbgUtil.showLog(TAG, "userId: " + userId);
				}

			} else {
				DbgUtil.showLog(TAG, "Twitter instance is null");
			}
		} else {
			DbgUtil.showLog(TAG, "Response id is null");
			builder.addErrorMessage("response id is null");
		}

		result = builder.getResultJson();

		return result;
	}

}
