package com.mame.wisdom.data;

import com.mame.wisdom.constant.WConstant;

public class WDUserDataBuilder {

	private final static String TAG = WDUserDataBuilder.class.getSimpleName();

	private WDUserData mUserData;

	private WDUserDataBuilder() {

	}

	private WDUserDataBuilder(WDUserData data) {
		mUserData = data;
	}

	public static WDUserDataBuilder createFrom(WDUserData data) {

		WDUserData userData;
		if (data != null) {
			userData = new WDUserData(data);
		} else {
			userData = new WDUserData(WConstant.NO_USER, null, null, null,
					null, null, null, null, null, 0);
		}

		WDUserDataBuilder builder = new WDUserDataBuilder(userData);

		return builder;
	}

	public WDUserDataBuilder setUserId(long userId) {
		mUserData.setUserId(userId);
		return this;
	}

	public WDUserDataBuilder setTwitterName(String twitterName) {
		mUserData.setTwitterName(twitterName);
		return this;
	}

	public WDUserDataBuilder setTwitterToken(String twitterToken) {
		mUserData.setTwitterToken(twitterToken);
		return this;
	}

	public WDUserDataBuilder setTwitterTokenSecret(String twitterTokenSecret) {
		mUserData.setTwitterTokenSecret(twitterTokenSecret);
		return this;
	}

	public WDUserDataBuilder setFacebookName(String facebookName) {
		mUserData.setFacebookName(facebookName);
		return this;
	}

	public WDUserDataBuilder setUsername(String userName) {
		mUserData.setUsername(userName);
		return this;
	}

	public WDUserDataBuilder setPassword(String password) {
		mUserData.setPassword(password);
		return this;
	}

	public WDUserDataBuilder setThumbnail(String thumbnail) {
		mUserData.setThumbnail(thumbnail);
		return this;
	}

	public WDUserDataBuilder setLastLoginDate(long loginDate) {
		mUserData.setLastLoginDate(loginDate);
		return this;
	}

	// public WDUserDataBuilder setTotalPoint(long point) {
	// mUserData.setTotalPoint(point);
	// return this;
	// }

	public WDUserDataBuilder setMailAddress(String mailAddress) {
		mUserData.setMailAddress(mailAddress);
		return this;
	}

	public WDUserData getConstructedData() {
		return mUserData;
	}
}
