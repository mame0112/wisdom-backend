package com.mame.wisdom.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

import com.mame.wisdom.twitter.TwitterConstant;
import com.mame.wisdom.util.DbgUtil;

public class TwitterCreateAccountAction implements Action {

	private final static String TAG = TwitterCreateAccountAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "TwitterCreateAccountAction execute");

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY,
				TwitterConstant.CONSUMER_SECRET);
		request.getSession().setAttribute(TwitterConstant.KEY_TWITTER, twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append(
					ActionConstants.KEY_TWITTER_CALLBACK);

			RequestToken requestToken = twitter
					.getOAuthRequestToken(callbackURL.toString());
			request.getSession().setAttribute(
					TwitterConstant.KEY_REQUEST_TOKEN, requestToken);
			response.sendRedirect(requestToken.getAuthenticationURL());

		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		} catch (IOException e) {
			DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
		}
		
		DbgUtil.showLog(TAG, "After twitter redirect");

		return null;
	}

}
