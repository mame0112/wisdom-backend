package com.mame.wisdom.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.DbgUtil;

public class ActionFactory {

	private final static String TAG = "ActionFactory";

	private final static Map<String, Action> mAction = new HashMap<String, Action>();

	static {
		DbgUtil.showLog(TAG, "static initializer");
		mAction.put(ActionConstants.GET + ActionConstants.SIGNIN_KEY,
				new SigninAction());
	}

	private ActionFactory() {

	}

	public static Action getAction(HttpServletRequest request) {
		DbgUtil.showLog(TAG,
				"getAction: " + request.getMethod() + request.getPathInfo());
		return mAction.get(request.getMethod() + request.getPathInfo());
	}
}
