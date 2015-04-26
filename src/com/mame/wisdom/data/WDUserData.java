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

	private String mMailAddress = null;

	private String mThumbnail = null;

	private long mLastLoginDate = 0;

	private long mTotalPoint = 0;

	public WDUserData(long userId, String twitterName, String twitterToken,
			String twitterTokenSecret, String facebookName, String userName,
			String password, String thumbnail, long lastLogin, long totalPoint,
			String mailAddress) {
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
		mMailAddress = mailAddress;
	}

	/**
	 * Copy constructor
	 * 
	 * @param data
	 */
	public WDUserData(WDUserData data) {
		if (data == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		mUserId = data.getUserId();
		mTwitterName = data.getTwitterName();
		mTwitterToken = data.getTwitterToken();
		mTwitterTokenSecret = data.getTwitterTokenSecret();
		mFacebookName = data.getFacebookName();
		mUserName = data.getUsername();
		mPassword = data.getPassword();
		mThumbnail = data.getThumbnail();
		mLastLoginDate = data.getLastLoginDate();
		mTotalPoint = data.getTotalPoint();

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

	public String getMailAddress() {
		return mMailAddress;
	}

	public void setTwitterName(String twitterName) {
		mTwitterName = twitterName;
	}

	public void setTwitterToken(String twitterToken) {
		mTwitterToken = twitterToken;
	}

	public void setTwitterTokenSecret(String twitterTokenSecret) {
		mTwitterTokenSecret = twitterTokenSecret;
	}

	public void setFacebookName(String facebookName) {
		mFacebookName = facebookName;
	}

	public void setUsername(String userName) {
		mUserName = userName;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public void setThumbnail(String thumbnail) {
		mThumbnail = thumbnail;
	}

	public void setLastLoginDate(long loginDate) {
		mLastLoginDate = loginDate;
	}

	public void setTotalPoint(long point) {
		mTotalPoint = point;
	}

	public void setMailAddress(String mailAddress) {
		mMailAddress = mailAddress;
	}
}
