package com.mame.wisdom.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
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

		JSONObject object = JsonParseUtil.parseWisdomDataToJsonObject(data);

		if (object != null) {
			DbgUtil.showLog(TAG, "object is not null");
			// This "process" is a name that is defined in queue.xml
			Queue queue = QueueFactory.getQueue(WORKER);
			TaskOptions to = TaskOptions.Builder.withUrl("/" + WORKER).param(
					SearchConstants.KEY, object.toString());
			queue.add(to.method(Method.POST));
		}
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
