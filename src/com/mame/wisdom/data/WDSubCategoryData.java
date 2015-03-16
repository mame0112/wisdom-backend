package com.mame.wisdom.data;

import java.util.ArrayList;
import java.util.List;

public class WDSubCategoryData {
	private long mCategoryId = 0;

	private long mSubCategoryId = 0;

	private String mCategoryName = null;

	private String mSubCategoryName = null;

	private String mCategoryDescription = null;

	private long mTotalWisdomNum = 0;

	private long mOffset = 0;

	private int mLimit = 10;

	private List<Long> mWisdomIds = new ArrayList<Long>();

	public WDSubCategoryData(long categoryId, long subCategoryId,
			String categoryName, String subCategoryName, String description,
			List<Long> wisdomIds, long totalNum, long offset, int limit) {
		mCategoryId = categoryId;
		mSubCategoryId = subCategoryId;
		mCategoryName = categoryName;
		mSubCategoryName = subCategoryName;
		mCategoryDescription = description;
		mWisdomIds = wisdomIds;
		mTotalWisdomNum = totalNum;
		mOffset = offset;
		mLimit = limit;
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

	public long getTotalWisdomNum() {
		return mTotalWisdomNum;
	}

	public long getOffset() {
		return mOffset;
	}

	public int getLimit() {
		return mLimit;
	}

}
