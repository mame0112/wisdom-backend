package com.mame.wisdom.datastore;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.util.DbgUtil;

public class DefaultWisdomDAO implements WisdomDAO {

	private final static String TAG = DefaultWisdomDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public List<WDWisdomData> getPopularWisdoms() {
		DbgUtil.showLog(TAG, "getPopularWisdoms");
		
		//TODO
		
		return null;
	}
}
