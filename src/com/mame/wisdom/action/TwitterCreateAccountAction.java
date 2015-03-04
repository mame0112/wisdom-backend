package com.mame.wisdom.action;

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

		TwitterFactory twitterFactory = new TwitterFactory();

		// Titterオブジェクトの生成
		Twitter twitter = twitterFactory.getInstance();
		Configuration c = twitter.getConfiguration();
		if (c.getOAuthConsumerKey() == null
				|| c.getOAuthConsumerSecret() == null) {
			twitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY,
					TwitterConstant.CONSUMER_SECRET);
		}

		try {
			DbgUtil.showLog(TAG, "AAA");
			// リクエストトークンの生成
			RequestToken reqToken = twitter.getOAuthRequestToken();

			DbgUtil.showLog(TAG, "BBB");

			// RequestTokenとTwitterオブジェクトをセッションに保存
			HttpSession session = request.getSession();
			session.setAttribute("RequestToken", reqToken);
			session.setAttribute("Twitter", twitter);

			DbgUtil.showLog(TAG, "CCC");

			// 認証画面にリダイレクトするためのURLを生成
			String strUrl = reqToken.getAuthorizationURL();
			DbgUtil.showLog(TAG, "DDD: " + strUrl);
			response.sendRedirect(strUrl);
			DbgUtil.showLog(TAG, "EEE");
		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}

		// WisdomTwitter twitter = new WisdomTwitter();
		// String authURL = twitter.getAuthorizationURL();
		// DbgUtil.showLog(TAG, "authURL: " + authURL);
		// if (authURL != null) {
		// response.sendRedirect(authURL);
		// } else {
		// DbgUtil.showLog(TAG, "authURL is null");
		// }

		return null;
	}

}
