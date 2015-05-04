package com.mame.wisdom.data;

import com.mame.wisdom.constant.WConstant;

public class WDSubCategoryKeyData {
	private String mCategory;

	private String mSubCategory;

	private long mWisdomId = WConstant.NO_WISDOM;

	public WDSubCategoryKeyData(String category, String subCategory,
			long wisdomId) {
		mCategory = category;
		mSubCategory = subCategory;
		mWisdomId = wisdomId;
	}

	public String getCategory() {
		return mCategory;
	}

	public String getSubCategory() {
		return mSubCategory;
	}

	public long getWisdomId() {
		return mWisdomId;
	}

}
