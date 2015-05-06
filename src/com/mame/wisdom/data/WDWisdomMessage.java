package com.mame.wisdom.data;

import com.mame.wisdom.constant.WConstant;

public class WDWisdomMessage implements WDWisdomItemEntry {

	private long mItemId = 0;

	private String mMessage = null;

	private int mNumOfLiked = 0;

	private long mLastUpdateUserId = WConstant.NO_USER;

	private String mLastUpdateUserName = null;

	private long mLastUpdateDate = 0;

	public WDWisdomMessage(long id, String message, int numOfLike,
			long lastUpdateUserId, String lastUpdateUserName, long updateDate) {
		mItemId = id;
		mMessage = message;
		mNumOfLiked = numOfLike;
		mLastUpdateUserId = lastUpdateUserId;
		mLastUpdateUserName = lastUpdateUserName;
		mLastUpdateDate = updateDate;
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

	@Override
	public long getLastUpdateDate() {
		return mLastUpdateDate;
	}

	@Override
	public void setItemId(long id) {
		mItemId = id;
	}

	@Override
	public void setItemLikeNum(int newLikeNum) {
		mNumOfLiked = newLikeNum;
	}

}
