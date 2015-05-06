package com.mame.wisdom.data;

import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.mame.wisdom.constant.WConstant;

public class WDWisdomDataBuilder {

	private final static String TAG = WDWisdomDataBuilder.class.getSimpleName();

	private WDWisdomData mWisdomData;

	private WDWisdomDataBuilder(WDWisdomData wisdomData) {
		mWisdomData = wisdomData;
	}

	public static WDWisdomDataBuilder createFrom(WDWisdomData wisdomData) {

		WDWisdomData data = null;
		if (wisdomData != null) {
			data = new WDWisdomData(wisdomData);
		} else {
			data = new WDWisdomData(WConstant.NO_WISDOM, null, null, null,
					WConstant.NO_USER, 0, null, null, 0);
		}

		WDWisdomDataBuilder builder = new WDWisdomDataBuilder(data);

		return builder;
	}

	public WDWisdomDataBuilder setWisdomId(long wisdomId) {
		mWisdomData.setWisdomId(wisdomId);
		return this;
	}

	public WDWisdomDataBuilder setTag(String tag) {
		mWisdomData.setTag(tag);
		return this;
	}

	public WDWisdomDataBuilder setTitle(String title) {
		mWisdomData.setTitle(title);
		return this;
	}

	public WDWisdomDataBuilder setDescription(String description) {
		mWisdomData.setDescription(description);
		return this;
	}

	public WDWisdomDataBuilder setThumbnail(Blob thumbnail) {
		mWisdomData.setThumbnail(thumbnail);
		return this;
	}

	public WDWisdomDataBuilder setCreatedUserId(long userId) {
		mWisdomData.setCreatedUserId(userId);
		return this;
	}

	public WDWisdomDataBuilder setLastUpdatedDate(long date) {
		mWisdomData.setLastUpdatedDate(date);
		return this;
	}

	public WDWisdomDataBuilder setViewCount(long viewCount) {
		mWisdomData.setViewCount(viewCount);
		return this;
	}

	public WDWisdomDataBuilder setEntires(List<WDWisdomItemEntry> entries) {
		mWisdomData.setEntries(entries);
		return this;
	}

	public WDWisdomData getWisdomData() {
		return mWisdomData;
	}

}
