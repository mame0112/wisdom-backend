package com.mame.wisdom;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
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

public class WisdomTwitterServlet extends HttpServlet {

	private final static String TAG = WisdomTwitterServlet.class
			.getSimpleName();

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		DbgUtil.showLog(TAG, "TwitterServlet service");

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
		} catch (IOException e) {
			DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
		}
	}

}
