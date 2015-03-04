package com.mame.wisdom.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mame.wisdom.exception.ActionException;
import com.mame.wisdom.util.DbgUtil;

public class ActionFactory {

	private final static String TAG = "ActionFactory";

	private final static Map<String, Action> mAction = new HashMap<String, Action>();

	static {
		DbgUtil.showLog(TAG, "static initializer");
		// Dummy APIs. To be removed.
		mAction.put(ActionConstants.GET + ActionConstants.KEY_SIGNIN,
				new SigninAction());
		mAction.put(ActionConstants.GET + ActionConstants.KEY_INFOBAR,
				new InfobarAction());

		// User APIs
		mAction.put(ActionConstants.GET + ActionConstants.KEY_USER,
				new UserAction());
		mAction.put(ActionConstants.POST + ActionConstants.KEY_USER,
				new UserAction());

		// Notification APIs
		mAction.put(ActionConstants.GET + ActionConstants.KEY_NOTIFICATION,
				new NotificationAction());
		mAction.put(ActionConstants.POST + ActionConstants.KEY_NOTIFICATION,
				new NotificationAction());

		// Wisdom APIs
		mAction.put(ActionConstants.GET + ActionConstants.KEY_WISDOM,
				new WisdomAction());
		mAction.put(ActionConstants.POST + ActionConstants.KEY_WISDOM,
				new WisdomAction());
		mAction.put(ActionConstants.PUT + ActionConstants.KEY_WISDOM,
				new WisdomAction());

		// Highlight information API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_HIGHLIGHT,
				new HighlightInfoAction());

		// Latest information API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_LATEST,
				new LatestInfoAction());

		// Search API
		mAction.put(ActionConstants.GET + ActionConstants.KEY_SEARCH,
				new SearchAction());

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
