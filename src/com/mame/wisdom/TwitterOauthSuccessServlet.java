package com.mame.wisdom;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mame.wisdom.util.DbgUtil;

public class TwitterOauthSuccessServlet extends HttpServlet {

	private final static String TAG = TwitterOauthSuccessServlet.class
			.getSimpleName();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String accessToken = request.getParameter("oauth_token");
		String tokenSecret = request.getParameter("oauth_verifier");

		if (accessToken != null) {
			DbgUtil.showLog(TAG, "accessToken: " + accessToken);
		} else {
			DbgUtil.showLog(TAG, "accessToken is null");
		}

		if (tokenSecret != null) {
			DbgUtil.showLog(TAG, "tokenSecret: " + tokenSecret);
		} else {
			DbgUtil.showLog(TAG, "tokenSecret is null");
		}

	}

}
