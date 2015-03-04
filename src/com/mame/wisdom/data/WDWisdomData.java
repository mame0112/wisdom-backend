package com.mame.wisdom.data;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;

public class WDWisdomData {

	private long mWisdomId = 0;

	private String mTag = null;

	private String mTitle = null;

	private String mDescription = null;

	private Blob mThumbnail = null;

	private long mCreatedUserId = WConstant.NO_USER;

	private long mLastUpdatedDate = 0;

	// TODO need to consider how we handle this.
	private List<Long> mBelongWisdomIds = new ArrayList<Long>();

	// TODO need to consider how we handle this.
	private List<Integer> mItemOrder = new ArrayList<Integer>();

	private List<WDWisdomItemEntry> mItems = new ArrayList<WDWisdomItemEntry>();

	public WDWisdomData(long id, String title, String description, String tag,
			long createdUserId, long lastUpdatedDate, Blob thumbnail,
			List<WDWisdomItemEntry> items) {
		mWisdomId = id;
		mTitle = title;
		mDescription = description;
		mTag = tag;
		mCreatedUserId = createdUserId;
		mLastUpdatedDate = lastUpdatedDate;
		mThumbnail = thumbnail;
		mItems = items;
	}

	public long getWisdomId() {
		return mWisdomId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getTag() {
		return mTag;
	}

	public Blob getThumbnakl() {
		return mThumbnail;
	}

	public long getCreatedUserId() {
		return mCreatedUserId;
	}

	public long getLastUpdatedDate() {
		return mLastUpdatedDate;
	}

	public List<WDWisdomItemEntry> getItems() {
		return mItems;
	}

}
