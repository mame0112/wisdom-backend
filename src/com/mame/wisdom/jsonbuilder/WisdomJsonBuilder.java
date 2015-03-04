package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class WisdomJsonBuilder extends JsonBuilder {

	private final static String TAG = WisdomJsonBuilder.class.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public WisdomJsonBuilder() {
		DbgUtil.showLog(TAG, "WisdomJsonBuilder");
		try {
			addVersion(VERSION);
		} catch (JSONBuilderException e) {
			DbgUtil.showLog(TAG, "JSONBuilderException: " + e.getMessage());
		}
	}

	@Override
	public void addResponseId(int id) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseId");

		try {
			mRootObject.put(JsonConstant.ID, id);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}

	}

	@Override
	protected void addVersion(String version) throws JSONBuilderException {
		try {
			mRootObject.put(JsonConstant.VERSION, version);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			throw new JSONBuilderException(e.getMessage());
		}

	}

	@Override
	public void addResponseParam(Object param) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseParam");

		if (!(param instanceof WDWisdomData)) {
			throw new JSONBuilderException("Illegal param type");
		}

		WDWisdomData data = (WDWisdomData) param;

		JSONObject obj = new JSONObject();
		try {

			List<WDWisdomItemEntry> content = data.getItems();

			obj.put(JsonConstant.PARAM_WISDOM_ID, data.getWisdomId());
			obj.put(JsonConstant.PARAM_WISDOM_TITLE, data.getTitle());
			obj.put(JsonConstant.PARAM_WISDOM_TAG, data.getTag());
			obj.put(JsonConstant.PARAM_WISDOM_DESCRIPTION,
					data.getDescription());
			obj.put(JsonConstant.PARAM_WISDOM_UPDATED_DATE,
					data.getLastUpdatedDate());
			obj.put(JsonConstant.PARAM_WISDOM_CREATE_USER_ID,
					data.getCreatedUserId());
			// obj.put(JsonConstant.param_wis );

			// TODO need to parse Wisdom content.
			mRootObject.put(JsonConstant.PARAMS, obj);

		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}

	}

	@Override
	public void addErrorMessage(String message) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addErrorMessage");
		try {
			mRootObject.put(JsonConstant.PARAM_ERROR_MESSAGE, message);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}
	}

	@Override
	public String getResultJson() throws JSONBuilderException {
		DbgUtil.showLog(TAG, "getResultJson");
		return mRootObject.toString();
	}

}
