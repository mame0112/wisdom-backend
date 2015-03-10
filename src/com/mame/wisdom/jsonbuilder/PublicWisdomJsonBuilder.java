package com.mame.wisdom.jsonbuilder;

import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.JSONBuilderException;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

public class PublicWisdomJsonBuilder extends JsonBuilder {

	private final static String TAG = PublicWisdomJsonBuilder.class
			.getSimpleName();

	private JSONObject mRootObject = new JSONObject();

	public PublicWisdomJsonBuilder() {
		DbgUtil.showLog(TAG, "HighlightInfoJsonBuilder");
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
		DbgUtil.showLog(TAG, TAG + "/addResponseParam");

		if (param == null) {
			throw new JSONBuilderException("param is null");
		}

		if (!(param instanceof List<?>)) {
			throw new JSONBuilderException("Illegal param type");
		}

		List<WDWisdomData> wisdoms = (List<WDWisdomData>) param;

		JSONArray array = JsonParseUtil.parseWisdomListToJsonArray(wisdoms);
		try {
			mRootObject.put(JsonConstant.PARAMS, array);
		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
			throw new JSONBuilderException(e.getMessage());
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
