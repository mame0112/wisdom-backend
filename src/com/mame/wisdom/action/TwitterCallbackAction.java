package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.mame.wisdom.datastore.UserDataFacade;
import com.mame.wisdom.twitter.TwitterConstant;
import com.mame.wisdom.util.DbgUtil;

public class TwitterCallbackAction implements Action {

	private final static String TAG = TwitterCallbackAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "TwitterCallbackAction execute");

		Twitter twitter = (Twitter) request.getSession().getAttribute(
				TwitterConstant.KEY_TWITTER);
		RequestToken requestToken = (RequestToken) request.getSession()
				.getAttribute(TwitterConstant.KEY_REQUEST_TOKEN);
		String verifier = request
				.getParameter(TwitterConstant.KEY_OAUTH_VERIFIER);
		try {
			twitter.getOAuthAccessToken(requestToken, verifier);
			request.getSession().removeAttribute(
					TwitterConstant.KEY_REQUEST_TOKEN);
			DbgUtil.showLog(TAG, "AA");

			AccessToken token = twitter.getOAuthAccessToken();

			UserDataFacade facade = new UserDataFacade();

			User user = twitter.showUser(twitter.getScreenName());

			// TODO need to store thumbnail
			facade.saveTwitterTokens(twitter.getScreenName(),
					user.getProfileImageURL(), token.getToken(),
					token.getTokenSecret());

		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/");

		return null;
	}
}
