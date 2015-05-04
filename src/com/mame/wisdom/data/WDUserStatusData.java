package com.mame.wisdom.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WDUserStatusData implements Serializable {

	private long mUserId = 0;

	// private String mUserName = null;

	private List<Long> mCreatedWisdomIds = new ArrayList<Long>();

	private List<Long> mLikedWisdomIds = new ArrayList<Long>();

	private long mTotalPoint = 0;

	public WDUserStatusData(long userId, long totalPoint,
			List<Long> createdWisdomIds, List<Long> likedWisdomIds) {
		mUserId = userId;
		mTotalPoint = totalPoint;
		mCreatedWisdomIds = createdWisdomIds;
		mLikedWisdomIds = likedWisdomIds;
	}

	public long getUserId() {
		return mUserId;
	}

	// public String getUsername() {
	// return mUserName;
	// }

	public long getTotalPoint() {
		return mTotalPoint;
	}

	public List<Long> getCreatedWisdomIds() {
		return mCreatedWisdomIds;
	}

	public List<Long> getLikedWisdomIds() {
		return mLikedWisdomIds;
	}

	// public void setUserId(long userId) {
	// mUserId = userId;
	// }
	//
	// public void setUsername(String userName) {
	// mUserName = userName;
	// }

	public void setTotalPoint(long point) {
		mTotalPoint = point;
	}

	public void addCreatedWisdomId(long createdWisdomId) {
		if (mCreatedWisdomIds == null) {
			mCreatedWisdomIds = new ArrayList<Long>();
		}
		mCreatedWisdomIds.add(createdWisdomId);

	}

	public void addLikedWisdomId(long likedWisdomId) {
		if (mLikedWisdomIds == null) {
			mLikedWisdomIds = new ArrayList<Long>();
		}
		mLikedWisdomIds.add(likedWisdomId);
	}

}
