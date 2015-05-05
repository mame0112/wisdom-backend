package com.mame.wisdom.data;

import java.io.Serializable;

import com.mame.wisdom.constant.WConstant;

public class WDHighlighPointUserData implements Serializable {

	private long mUserId = WConstant.NO_USER;

	private String mUserName;

	private long mPoint;

	public WDHighlighPointUserData(long userId, String userName, long point) {
		mUserId = userId;
		mUserName = userName;
		mPoint = point;
	}
	
	public long getUserId() {
		return mUserId;
	}

	public String getUserName() {
		return mUserName;
	}

	public long getPoint() {
		return mPoint;
	}

}
