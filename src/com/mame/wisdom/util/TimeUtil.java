package com.mame.wisdom.util;

import java.util.Date;

public class TimeUtil {

	private final static String TAG = TimeUtil.class.getSimpleName();
	
	public static long getCurrentDate() {
		Date date = new Date();
		return date.getTime();
	}

}
