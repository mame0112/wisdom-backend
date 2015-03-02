package com.mame.wisdom.mock;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mame.wisdom.datastore.DBConstant;
import com.mame.wisdom.util.DbgUtil;

public class MockJSONGenerator {

	private final static String TAG = MockJSONGenerator.class.getSimpleName();

	public String generateMockSigninJSON() {

		return null;
	}

	public static String generateMockNewWisdomJSON() {
		DbgUtil.showLog(TAG, "generateMockNewWisdomJSON");

		JSONArray rootArray = new JSONArray();
		try {
			JSONObject obj1 = new JSONObject();
			obj1.put(DBConstant.ENTITY_ID, 1);
			obj1.put(DBConstant.ENTITY_TITLE, "title 1");
			obj1.put(DBConstant.ENTITY_DESCRIPTION, "description 1");

			JSONObject obj2 = new JSONObject();
			obj2.put(DBConstant.ENTITY_ID, 2);
			obj2.put(DBConstant.ENTITY_TITLE, "title 2");
			obj2.put(DBConstant.ENTITY_DESCRIPTION, "description 2");

			rootArray.put(obj1);
			rootArray.put(obj2);

			return rootArray.toString();

		} catch (JSONException e) {
			DbgUtil.showLog(TAG, "JSONException: " + e.getMessage());
		}
		// JSONObject rootObject = new JSONObject();

		return null;
	}

}
