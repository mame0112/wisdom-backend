package com.mame.wisdom.util;

import java.io.UnsupportedEncodingException;

import com.google.appengine.api.datastore.Blob;

public class DatastoreUtil {

	private final static String TAG = DatastoreUtil.class.getSimpleName();

	public static Blob transcodeString2Blob(String origin) {
		DbgUtil.showLog(TAG, "transcodeString2Blob");
		if (origin != null) {
			try {
				byte[] bytes = origin.getBytes("UTF-8");
				return new Blob(bytes);
			} catch (UnsupportedEncodingException e) {
				DbgUtil.showLog(TAG,
						"UnsupportedEncodingException: " + e.getMessage());
			}
		}
		return null;
	}

	public static String transcodeBlob2String(Blob origin) {
		DbgUtil.showLog(TAG, "transcodeBlob2String");
		if (origin != null) {
			byte[] bytes = origin.getBytes();
			if (bytes != null) {
				try {
					return new String(bytes, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					DbgUtil.showLog(TAG,
							"UnsupportedEncodingException: " + e.getMessage());
				}
			}
		}
		return null;
	}
}
