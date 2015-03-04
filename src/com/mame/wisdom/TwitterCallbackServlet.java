package com.mame.wisdom;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mame.wisdom.twitter.TwitterConstant;
import com.mame.wisdom.util.DbgUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

public class TwitterCallbackServlet extends HttpServlet {

	private final static String TAG = TwitterCallbackServlet.class
			.getSimpleName();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String oauth_token = (String) req.getParameter("oauth_token");
		String verifier = (String) req.getParameter("oauth_verifier");
		DbgUtil.showLog(TAG, "oauth_token: " + oauth_token);
		DbgUtil.showLog(TAG, "verifier: " + verifier);
		// HttpSession session = req.getSession();
		// Twitter twitter = (Twitter) session.getAttribute("Twitter");
		// String verifier = req.getParameter("oauth_verifier");

		try {
			TwitterFactory twitterFactory = new TwitterFactory();
			Twitter twitter = twitterFactory.getInstance();

			twitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY,
					TwitterConstant.CONSUMER_SECRET);

			RequestToken requestToken = twitter.getOAuthRequestToken();
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken,
					verifier);
			DbgUtil.showLog(TAG, "OAuthToken: " + accessToken.getToken());
			DbgUtil.showLog(TAG,
					"OAuthTokenSecret: " + accessToken.getTokenSecret());

		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}

		// try {
		// // RequestTokenからAccessTokenを取得
		// // accessToken = twitter.getOAuthAccessToken(
		// // (RequestToken) session.getAttribute("RequestToken"),
		// // verifier);
		// accessToken = twitter.getOAuthAccessToken(reqToken, verifier);
		// accessToken.getTokenSecret();
		// } catch (TwitterException e) {
		// DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		// }
		//
		// if (accessToken != null) {
		// DbgUtil.showLog(TAG, "access token is not null");
		// // AccessTokenをセッションに保持
		// // session.setAttribute("AccessToken", accessToken.getToken());
		// // session.setAttribute("AccessTokenSecret",
		// // accessToken.getTokenSecret());
		// resp.sendRedirect("/twitteroauth");
		// } else {
		// DbgUtil.showLog(TAG, "else");
		// resp.setContentType("text/plain");
		// resp.getWriter().println("AccessTokenがNullってます！");
		// }
	}
}
