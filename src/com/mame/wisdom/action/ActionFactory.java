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
		mAction.put(ActionConstants.GET + ActionConstants.SIGNIN_KEY,
				new SigninAction());
		mAction.put(ActionConstants.GET + ActionConstants.INFOBAR_KEY,
				new InfobarAction());
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
