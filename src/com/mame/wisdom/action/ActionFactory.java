package com.mame.wisdom.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mame.wisdom.exception.ActionException;
import com.mame.wisdom.util.DbgUtil;

public class ActionFactory {

	private final static String TAG = ActionFactory.class.getSimpleName();

	private final static Map<String, Action> mAction = new HashMap<String, Action>();

	static {
		DbgUtil.showLog(TAG, "static initializer");
		// Dummy APIs. To be removed.
		mAction.put(ActionConstants.GET + ActionConstants.KEY_SIGNIN,
				new SigninAction());
		mAction.put(ActionConstants.GET + ActionConstants.KEY_INFOBAR,
				new InfobarAction());

		// User APIs
		// Sign in
		mAction.put(ActionConstants.GET + ActionConstants.KEY_USER,
				new SigninAction());
		// Sign up (Create account)
		mAction.put(ActionConstants.POST + ActionConstants.KEY_USER,
				new SignupAction());

		// Get current user status API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_USERINFO,
				new UserStatusAction());

		// Get current user status API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_USERRANKING,
				new UserPointRankingAction());

		// Notification APIs
		// TODO TO be updated.
		// Show notification later.
		mAction.put(ActionConstants.GET + ActionConstants.KEY_NOTIFICATION,
				new NotificationAction());
		// Update notification read state after the user check it.
		mAction.put(ActionConstants.POST + ActionConstants.KEY_NOTIFICATION,
				new NotificationAction());

		// Category API
		// Get category
		mAction.put(ActionConstants.GET + ActionConstants.KEY_CATEGORY,
				new CategoryAction());

		// Wisdom APIs
		// Get wisdom(s)
		// TODO to be checked if we can reuse this action for single and
		// multiple cases.
		mAction.put(ActionConstants.GET + ActionConstants.KEY_WISDOM,
				new WisdomAction());
		// Create new wisdom
		mAction.put(ActionConstants.GET + ActionConstants.KEY_NEW_WISDOM,
				new CreateWisdomAction());

		// Update wisdom
		mAction.put(ActionConstants.PUT + ActionConstants.KEY_WISDOM,
				new UpdateWisdomAction());

		// Highlight information API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_HIGHLIGHT,
				new HighlightInfoAction());

		// Latest information API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_LATEST,
				new LatestInfoAction());

		// Search API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_SEARCH,
				new SearchAction());

		// Twitter API
		// Sign in with Twitter
		mAction.put(ActionConstants.GET + ActionConstants.KEY_TWITTER,
				new TwitterSigninAction());

		// Create account by Twitter (This is not API)
		mAction.put(ActionConstants.GET + ActionConstants.KEY_TWITTER_SIGNUP,
				new TwitterCreateAccountAction());

		// Callback action by twitter
		mAction.put(ActionConstants.GET + ActionConstants.KEY_TWITTER_CALLBACK,
				new TwitterCallbackAction());

		// Debug action
		mAction.put(ActionConstants.GET + ActionConstants.KEY_DEBUG,
				new DebugAction());

	}

	private ActionFactory() {

	}

	public static Action getAction(HttpServletRequest request)
			throws ActionException {

		String method = request.getMethod();
		String path = request.getPathInfo();

		if (method == null || path == null) {
			DbgUtil.showLog(TAG, "getAction method or pathinfo is null");
			throw new ActionException("method or path is null");
		}

		DbgUtil.showLog(TAG, "getAction: " + method + path);
		return mAction.get(method + path);

	}
}
