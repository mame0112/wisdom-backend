package com.mame.wisdom.data;

public class WDUserData {
	
	private long mUserId = 0;

	public WDUserData(long userId){
		mUserId = userId;
	}
	
	public long getUserId(){
		return mUserId;
	}
}
