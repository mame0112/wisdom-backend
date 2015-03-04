package com.mame.wisdom.data;

import java.util.ArrayList;
import java.util.List;

public class WDSubCategoryData {
	private long mCategoryId = 0;

	private String mDescription = null;

	private List<Long> mWisdomIds = new ArrayList<Long>();

	public WDSubCategoryData(long id, String description, List<Long> wisdomIds) {
		mCategoryId = id;
		mDescription = description;
		mWisdomIds = wisdomIds;
	}

	public long getCategoryId() {
		return mCategoryId;
	}

	public String getDescription() {
		return mDescription;
	}

	public List<Long> getWisdomIds() {
		return mWisdomIds;
	}
}
