package com.mame.wisdom.search;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.JsonParseUtil;

public class WisdomSearchService {

	private final static String TAG = WisdomSearchService.class.getSimpleName();

	// private final static String ENQUEUE = "process";

	private final static String WORKER = "worker";

	public void addValue(WDWisdomData data) {
		DbgUtil.showLog(TAG, "addValue");
		if (data == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		JSONArray array = JsonParseUtil.parseWisdomItemEntitiesToJsonArray(data
				.getItems());
		// JSONObject object = JsonParseUtil.parseWisdomDataToJsonObject(data);
		// DbgUtil.showLog(TAG, "object:" + object.toString());

		// if (object != null) {
		DbgUtil.showLog(TAG, "object is not null");
		// This "process" is a name that is defined in queue.xml
		Queue queue = QueueFactory.getQueue(WORKER);
		// TaskOptions to = TaskOptions.Builder.withUrl("/" + WORKER).param(
		// SearchConstants.KEY, data.toString());
		// TaskOptions to = TaskOptions.Builder.withUrl("/" + WORKER)
		// .payload(object.toString())
		// .header("Content-type", "application/json");
		TaskOptions to = TaskOptions.Builder
				.withUrl("/" + WORKER)
				.param(SearchConstants.KEY, String.valueOf(data.getWisdomId()))
				.param(SearchConstants.KEY_SERACH_DESCRIPTION,
						String.valueOf(data.getDescription()))
				.param(SearchConstants.KEY_SERACH_TAG, data.getTag())
				.param(SearchConstants.KEY_SERACH_ITEM, array.toString())
				.param(SearchConstants.KEY_SERACH_TITLE, data.getTitle());

		queue.add(to.method(Method.POST));
		// }
	}

	public void storeNewWisdom(WDWisdomData data) {

		DbgUtil.showLog(TAG, "storeNewWisdom");
		if (data == null) {
			return;
		}

	}

	// TODO To be updated later
	public void updateWisdom(WDWisdomData data) {
		if (data == null) {
			return;
		}
	}

}
