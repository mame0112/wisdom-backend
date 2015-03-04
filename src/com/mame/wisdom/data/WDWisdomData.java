package com.mame.wisdom.data;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;

public class WDWisdomData {

	private long mWisdomId = 0;

	private String mTitle = null;

	private String mDescription = null;

	private Blob mThumbnail = null;

	private long mCreatedUserId = WConstant.NO_USER;

	private long mLastUpdatedDate = 0;

	private List<Long> mBelongMessageIds = new ArrayList<Long>();

	private List<Integer> mItemOrder = new ArrayList<Integer>();

	private String mTag = null;

	private List<String> mMessages = new ArrayList<String>();

	public WDWisdomData(long id, String title, String description, String tag) {
		mWisdomId = id;
		mTitle = title;
		mDescription = description;
		mTag = tag;
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

	// TODO Have to consider if we have messages and titles here directly (or it
	// is better to have another data class for them?)
	public List<String> getMessages() {
		return mMessages;
	}

}
