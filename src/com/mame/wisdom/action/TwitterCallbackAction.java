package com.mame.wisdom.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
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

			AccessToken token = twitter.getOAuthAccessToken();

			UserDataFacade facade = new UserDataFacade();

			User user = twitter.showUser(twitter.getScreenName());

			WDUserData data = facade.saveTwitterTokens(twitter.getScreenName(),
					user.getProfileImageURL(), token.getToken(),
					token.getTokenSecret());

			if (data != null) {
				DbgUtil.showLog(TAG, "userId: " + data.getUserId());
				HttpSession session = request.getSession();
				session.setAttribute("AA", data.getUserId());
			}

		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}

		Cookie cookie1 = new Cookie("name", "Bule");
		response.addCookie(cookie1);

		DbgUtil.showLog(TAG, "URL: " + request.getContextPath());
		response.sendRedirect(request.getContextPath() + "/#/signin");

		// JSONObject obj = new JSONObject();
		// obj.put("name", "Bule");

		// RequestDispatcher dispatch = request.getRequestDispatcher("/");
		// dispatch.forward(request, response);

		// String json = new Gson().toJson(CipherUtil.encryptArrayList(list,
		// identifier));
		// String json = new JSONObject().put("name", "test name").toString();
		// response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(json);

		return null;
	}
}
