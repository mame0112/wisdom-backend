package com.mame.wisdom.data;

import com.mame.wisdom.constant.WConstant;

public class WDWisdomMessage implements WDWisdomItemEntry {

	private long mItemId = 0;

	private String mMessage = null;

	private int mNumOfLiked = 0;

	private long mLastUpdateUserId = WConstant.NO_USER;

	private String mLastUpdateUserName = null;

	public WDWisdomMessage(long id, String message, int numOfLike,
			long lastUpdateUserId, String lastUpdateUserName) {
		mItemId = id;
		mMessage = message;
		mNumOfLiked = numOfLike;
		mLastUpdateUserId = lastUpdateUserId;
		mLastUpdateUserName = lastUpdateUserName;
	}

	@Override
	public long getItemId() {
		return mItemId;
	}

	@Override
	public String getItem() {
		return mMessage;
	}

	@Override
	public int getTag() {
		return WConstant.TAG_WISDOM_MESSAGE;
	}

	@Override
	public int getNumberOfLike() {
		return mNumOfLiked;
	}

	@Override
	public long getLastUpdateUserId() {
		return mLastUpdateUserId;
	}

	@Override
	public String getLastUpdateUserName() {
		return mLastUpdateUserName;
	}

}
