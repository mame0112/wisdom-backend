package com.mame.wisdom.data;

import com.mame.wisdom.constant.WConstant;

public class WDWisdomTitle implements WDWisdomItemEntry {
	private long mItemId = 0;

	private String mTitle = null;

	private int mNumOfLiked = 0;

	private long mLastUpdateUserId = WConstant.NO_USER;

	private String mLastUpdateUserName = null;

	private long mLastUpdateDate = 0;

	public WDWisdomTitle(long id, String title, int numOfLike,
			long lastUpdateUserId, String lastUpdateUserName, long updateDate) {
		mItemId = id;
		mTitle = title;
		mLastUpdateUserId = lastUpdateUserId;
		mLastUpdateUserName = lastUpdateUserName;
	}

	@Override
	public long getItemId() {
		return mItemId;
	}

	@Override
	public String getItem() {
		return mTitle;
	}

	@Override
	public int getTag() {
		return WConstant.TAG_WISDOM_TITLE;
	}

	@Override
	public int getNumberOfLike() {
		// Since we don't nave "Like" indication on Title, then this always
		// returns 0.
		return 0;
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
}
