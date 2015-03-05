//package com.mame.wisdom;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.mame.wisdom.twitter.TwitterConstant;
//import com.mame.wisdom.util.DbgUtil;
//
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.AccessToken;
//import twitter4j.auth.RequestToken;
//import twitter4j.conf.Configuration;
//
//public class TwitterCallbackServlet extends HttpServlet {
//
//	private final static String TAG = TwitterCallbackServlet.class
//			.getSimpleName();
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//
//		Twitter twitter = (Twitter) request.getSession()
//				.getAttribute("twitter");
//		RequestToken requestToken = (RequestToken) request.getSession()
//				.getAttribute("requestToken");
//		String verifier = request.getParameter("oauth_verifier");
//		try {
//			twitter.getOAuthAccessToken(requestToken, verifier);
//			request.getSession().removeAttribute("requestToken");
//			DbgUtil.showLog(TAG, "AA");
//		} catch (TwitterException e) {
//			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
//		}
//		response.sendRedirect(request.getContextPath() + "/");
//	}
//}
