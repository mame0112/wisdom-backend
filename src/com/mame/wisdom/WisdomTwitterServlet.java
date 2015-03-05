//package com.mame.wisdom;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.RequestToken;
//import twitter4j.conf.Configuration;
//
//import com.mame.wisdom.twitter.TwitterConstant;
//import com.mame.wisdom.util.DbgUtil;
//
//public class WisdomTwitterServlet extends HttpServlet {
//
//	private final static String TAG = WisdomTwitterServlet.class
//			.getSimpleName();
//
//	@Override
//	public void service(HttpServletRequest request, HttpServletResponse response) {
//		// DbgUtil.showLog(TAG, "TwitterServlet service");
//		//
//		// Twitter twitter = new TwitterFactory().getInstance();
//		// twitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY,
//		// TwitterConstant.CONSUMER_SECRET);
//		// request.getSession().setAttribute("twitter", twitter);
//		// try {
//		// StringBuffer callbackURL = request.getRequestURL();
//		// int index = callbackURL.lastIndexOf("/");
//		// callbackURL.replace(index, callbackURL.length(), "").append(
//		// "/twitterCallback");
//		//
//		// RequestToken requestToken = twitter
//		// .getOAuthRequestToken(callbackURL.toString());
//		// request.getSession().setAttribute("requestToken", requestToken);
//		// response.sendRedirect(requestToken.getAuthenticationURL());
//		//
//		// } catch (TwitterException e) {
//		// DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
//		// } catch (IOException e) {
//		// DbgUtil.showLog(TAG, "IOException: " + e.getMessage());
//		// }
//	}
//}
