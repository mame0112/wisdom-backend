package com.mame.wisdom.data;

import com.google.appengine.api.datastore.Blob;

public class WDUserData {

	private long mUserId = 0;

	private String mTwitterName = null;

	private String mFacebookName = null;

	private String mUserName = null;

	private String mPassword = null;

	private Blob mThumbnail = null;

	private long mLastLoginDate = 0;

	private long mTotalPoint = 0;

	public WDUserData(long userId, String twitterName, String facebookName,
			String userName, String password, Blob thumbnail, long lastLogin,
			long totalPoint) {
		mUserId = userId;
		mTwitterName = twitterName;
		mFacebookName = facebookName;
		mUserName = userName;
		mPassword = password;
		mThumbnail = thumbnail;
		mLastLoginDate = lastLogin;
		mTotalPoint = totalPoint;
	}

	public long getUserId() {
		return mUserId;
	}

	public String getTwitterName() {
		return mTwitterName;
	}

	public String getFacebookName() {
		return mFacebookName;
	}

	public String getUsername() {
		return mUserName;
	}

	public String getPassword() {
		return mPassword;
	}

	public Blob getThumbnail() {
		return mThumbnail;
	}

	public long getLastLoginDate() {
		return mLastLoginDate;
	}

	public long getTotalPoint() {
		return mTotalPoint;
	}
}
