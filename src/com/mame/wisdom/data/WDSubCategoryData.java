package com.mame.wisdom.data;

import java.util.ArrayList;
import java.util.List;

public class WDSubCategoryData {
	private long mCategoryId = 0;

	private long mSubCategoryId = 0;

	private String mCategoryName = null;

	private String mSubCategoryName = null;

	private String mCategoryDescription = null;

	private List<Long> mWisdomIds = new ArrayList<Long>();

	public WDSubCategoryData(long categoryId, long subCategoryId,
			String categoryName, String subCategoryName, String description,
			List<Long> wisdomIds) {
		mCategoryId = categoryId;
		mSubCategoryId = subCategoryId;
		mCategoryName = categoryName;
		mSubCategoryName = subCategoryName;
		mCategoryDescription = description;
		mWisdomIds = wisdomIds;
	}

	public long getCategoryId() {
		return mCategoryId;
	}

	public long getSubCategoryId() {
		return mSubCategoryId;
	}

	public String getCategoryName() {
		return mCategoryName;
	}

	public String getSubCategoryName() {
		return mSubCategoryName;
	}

	public String getDescription() {
		return mCategoryDescription;
	}

	public List<Long> getWisdomIds() {
		return mWisdomIds;
	}

}
