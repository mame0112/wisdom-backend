package com.mame.wisdom.data;

public class WisdomDataStructure {

	private WDWisdomData mWisdomData = null;

	private String mCategory = null;

	private String mSubCategory = null;

	public WisdomDataStructure(String category, String subCategory,
			WDWisdomData data) {
		mWisdomData = data;
		mCategory = category;
		mSubCategory = subCategory;
	}

	public WDWisdomData getWisdomData() {
		return mWisdomData;
	}

	public String getCategory() {
		return mCategory;
	}

	public String getSubCategory() {
		return mSubCategory;
	}

}
