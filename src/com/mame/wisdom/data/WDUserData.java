package com.mame.wisdom.data;

import java.io.Serializable;

public class WDUserData implements Serializable {

	private long mUserId = 0;

	private String mTwitterName = null;

	private String mTwitterToken = null;

	private String mTwitterTokenSecret = null;

	private String mFacebookName = null;

	private String mUserName = null;

	private String mPassword = null;

	private String mThumbnail = null;

	private long mLastLoginDate = 0;

	private long mTotalPoint = 0;

	public WDUserData(long userId, String twitterName, String twitterToken,
			String twitterTokenSecret, String facebookName, String userName,
			String password, String thumbnail, long lastLogin, long totalPoint) {
		mUserId = userId;
		mTwitterName = twitterName;
		mTwitterToken = twitterToken;
		mTwitterTokenSecret = twitterTokenSecret;
		mFacebookName = facebookName;
		mUserName = userName;
		mPassword = password;
		mThumbnail = thumbnail;
		mLastLoginDate = lastLogin;
		mTotalPoint = totalPoint;
	}

	public void setUserId(long userId) {
		mUserId = userId;
	}

	public long getUserId() {
		return mUserId;
	}

	public String getTwitterName() {
		return mTwitterName;
	}

	public String getTwitterToken() {
		return mTwitterToken;
	}

	public String getTwitterTokenSecret() {
		return mTwitterTokenSecret;
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

	public String getThumbnail() {
		return mThumbnail;
	}

	public long getLastLoginDate() {
		return mLastLoginDate;
	}

	public long getTotalPoint() {
		return mTotalPoint;
	}
}
