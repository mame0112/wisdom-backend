package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;

public class InfobarJsonBuilder extends JsonBuilder {

	private final static String TAG = InfobarJsonBuilder.class.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public InfobarJsonBuilder() {
		DbgUtil.showLog(TAG, "InfobarJsonBuilder");
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
		DbgUtil.showLog(TAG, "addVersion");
		try {
			mRootObject.put(JsonConstant.VERSION, version);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			throw new JSONBuilderException(e.getMessage());
		}
	}

	@Override
	public void addResponseParam(Object... param) throws JSONBuilderException {
		DbgUtil.showLog(TAG, "addResponseParam");
		if (param == null) {
			throw new JSONBuilderException("param is null");
		}

		// If instance type is not correct
		if (!(param[0] instanceof List<?>)) {
			throw new JSONBuilderException("illegal instance type");
		}

		List<WDWisdomData> data = (List<WDWisdomData>) param[0];

		try {
			JSONArray itemArray = new JSONArray();

			for (WDWisdomData d : data) {
				JSONObject itemObject = new JSONObject();
				itemObject.put(JsonConstant.PARAM_WISDOM_ID, d.getWisdomId());
				itemObject.put(JsonConstant.PARAM_WISDOM_TITLE, d.getTitle());
				itemObject.put(JsonConstant.PARAM_WISDOM_DESCRIPTION,
						d.getDescription());
				itemObject.put(JsonConstant.PARAM_WISDOM_TAG, d.getTag());
				itemArray.put(itemObject);
			}

			mRootObject.put(JsonConstant.PARAMS, itemArray);

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
