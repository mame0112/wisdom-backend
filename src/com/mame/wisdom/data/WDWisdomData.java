package com.mame.wisdom.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;

public class WDWisdomData implements Serializable {

	private long mWisdomId = 0;

	private String mTag = null;

	private String mTitle = null;

	private String mDescription = null;

	private Blob mThumbnail = null;

	private long mCreatedUserId = WConstant.NO_USER;

	private long mLastUpdatedDate = 0;

	private long mViewCount = 0;

	private List<Long> mItemOrder = new ArrayList<Long>();

	private List<WDWisdomItemEntry> mItems = new ArrayList<WDWisdomItemEntry>();

	public WDWisdomData(long id, String title, String description, String tag,
			long createdUserId, long lastUpdatedDate, Blob thumbnail,
			List<WDWisdomItemEntry> items, long viewCount) {
		mWisdomId = id;
		mTitle = title;
		mDescription = description;
		mTag = tag;
		mCreatedUserId = createdUserId;
		mLastUpdatedDate = lastUpdatedDate;
		mThumbnail = thumbnail;
		mItems = items;
		mViewCount = viewCount;
	}

	/*
	 * Copy constructor
	 */
	public WDWisdomData(WDWisdomData data) {
		if (data == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		mWisdomId = data.getWisdomId();
		mTitle = data.getTitle();
		mDescription = data.getDescription();
		mTag = data.getTag();
		mCreatedUserId = data.getCreatedUserId();
		mLastUpdatedDate = data.getLastUpdatedDate();
		mThumbnail = data.getThumbnail();
		mItems = data.getItems();
		mViewCount = data.getViewCount();
	}

	public long getWisdomId() {
		return mWisdomId;
	}

	public void setWisdomId(long id) {
		mWisdomId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	public String getTag() {
		return mTag;
	}

	public void setTag(String tag) {
		mTag = tag;
	}

	public Blob getThumbnail() {
		return mThumbnail;
	}

	public void setThumbnail(Blob thumbnail) {
		mThumbnail = thumbnail;
	}

	public long getCreatedUserId() {
		return mCreatedUserId;
	}

	public void setCreatedUserId(long id) {
		mCreatedUserId = id;
	}

	public long getLastUpdatedDate() {
		return mLastUpdatedDate;
	}

	public void setLastUpdatedDate(long date) {
		mLastUpdatedDate = date;
	}

	public List<WDWisdomItemEntry> getItems() {
		return mItems;
	}

	public void setItems(List<WDWisdomItemEntry> items) {
		mItems = items;
	}

	public long getViewCount() {
		return mViewCount;
	}

	public void setViewCount(long count) {
		mViewCount = count;
	}

	public void increaseViewCount() {
		mViewCount = mViewCount + 1;
	}

	public void setEntries(List<WDWisdomItemEntry> entries) {
		// setEntriesOrder(entries);
		mItems = entries;
	}

	public void setEntryOrder(List<Long> itemOrder) {
		mItemOrder = itemOrder;
	}

	// private void setEntriesOrder(List<WDWisdomItemEntry> entries) {
	// for (WDWisdomItemEntry entry : entries) {
	// mItemOrder.add(entry.getItemId());
	// }
	// }

	public List<Long> getEntriesOrder() {
		return mItemOrder;
	}

}
