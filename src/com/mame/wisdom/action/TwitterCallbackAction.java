package com.mame.wisdom.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import com.mame.wisdom.util.DbgUtil;

public class TwitterCallbackAction implements Action {

	private final static String TAG = TwitterCallbackAction.class
			.getSimpleName();

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DbgUtil.showLog(TAG, "TwitterCallbackAction execute");

		Twitter twitter = (Twitter) request.getSession()
				.getAttribute("twitter");
		RequestToken requestToken = (RequestToken) request.getSession()
				.getAttribute("requestToken");
		String verifier = request.getParameter("oauth_verifier");
		try {
			twitter.getOAuthAccessToken(requestToken, verifier);
			request.getSession().removeAttribute("requestToken");
			DbgUtil.showLog(TAG, "AA");
		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + "/");

		return null;
	}

}
