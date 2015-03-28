package com.mame.wisdom.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mame.wisdom.WisdomControllerServlet;
import com.mame.wisdom.constant.WConstant;

public class DbgUtil {

	public final static Logger log = Logger
			.getLogger(WisdomControllerServlet.class.getName());

	private final static String TAG = DbgUtil.class.getSimpleName();

	private long mLastTime = 0;

	public static void showLog(String tag, String message) {
		if (WConstant.IS_DEBUG) {
			if (message != null) {
				log.log(Level.WARNING, tag + ": " + message);
			}
		}
	}

	public void showTime(String identifier) {

		long now = TimeUtil.getCurrentDate();

		long diff = now - mLastTime;
		if (mLastTime != 0) {
			if (identifier != null) {
				DbgUtil.showLog(TAG, identifier + " time: " + diff);
			} else {
				DbgUtil.showLog(TAG, "time: " + diff);
			}

		}
		mLastTime = now;
	}

}
