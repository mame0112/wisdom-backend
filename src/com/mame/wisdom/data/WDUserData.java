package com.mame.wisdom.data;

public class WDUserData {

	private long mUserId = 0;

	private String mTwitterName;

	public WDUserData(long userId, String twitterName) {
		mUserId = userId;
		mTwitterName = twitterName;
	}

	public long getUserId() {
		return mUserId;
	}
	
	public String getTwitterName() {
		return mTwitterName;
	}
}
