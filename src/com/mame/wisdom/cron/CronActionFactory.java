package com.mame.wisdom.cron;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mame.wisdom.action.ActionConstants;
import com.mame.wisdom.exception.CronActionException;
import com.mame.wisdom.util.DbgUtil;

public class CronActionFactory {
	private final static String TAG = CronActionFactory.class.getSimpleName();

	private final static Map<String, CronAction> mAction = new HashMap<String, CronAction>();

	static {
		DbgUtil.showLog(TAG, "static initializer");
		mAction.put(ActionConstants.GET + CronConstant.KEY_LATESTINFO,
				new LatestInfoCronAction());
	}

	private CronActionFactory() {

	}

	public static CronAction getCronAction(HttpServletRequest request)
			throws CronActionException {
		String method = request.getMethod();
		String path = request.getPathInfo();

		if (method == null || path == null) {
			DbgUtil.showLog(TAG, "getAction method or pathinfo is null");
			throw new CronActionException("method or path is null");
		}

		DbgUtil.showLog(TAG, "getAction: " + method + path);
		return mAction.get(method + path);
	}

}
