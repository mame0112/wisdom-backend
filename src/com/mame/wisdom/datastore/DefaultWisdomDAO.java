package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DefaultWisdomDAO implements WisdomDAO {

	private final static String TAG = DefaultWisdomDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public List<WDWisdomData> getPopularWisdoms() {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		// TODO

		return null;
	}

	@Override
	public List<WDWisdomData> getWisdoms(String category, String subCategory)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdoms");

		// Get target SubCategory key
		Key key = DatastoreKeyGenerator
				.getSubCategoryKey(category, subCategory);

		Query query = new Query(DBConstant.KIND_WISDOM, key);
		PreparedQuery pQuery = mDS.prepare(query);

		// TODO We have to consider offset later on
		FetchOptions option = FetchOptions.Builder.withOffset(0);
		List<Entity> allEntities = pQuery.asList(option);

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
		List<WDWisdomData> result = helper
				.parseListEntityToWDWisdomData(allEntities);

		return result;
	}

	@Override
	public WDWisdomData getWisdom(String category, String subCategory,
			long wisdomId) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdom");

		Key key = DatastoreKeyGenerator.getWisdomKeyById(category, subCategory,
				wisdomId);
		Query query = new Query(DBConstant.KIND_WISDOM, key);
		PreparedQuery pQuery = mDS.prepare(query);
		Entity entity = pQuery.asSingleEntity();

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
		return helper.parseEntityToWDWisdomData(entity);
	}

	@Override
	public void addWisdom(String category, String subCategory,
			WDWisdomData wisdom) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "addWisdom");

		if (category == null || subCategory == null) {
			throw new WisdomDatastoreException(
					"category or subCategory parameter is null");
		}

		if (wisdom == null) {
			throw new WisdomDatastoreException("WDWisdomData is null");
		}

		long wisdomId = wisdom.getWisdomId();

		if (wisdomId != WConstant.NO_WISDOM) {
			throw new WisdomDatastoreException("Illegal wisdom Id");
		}

		Key key = DatastoreKeyGenerator.getWisdomKeyById(category, subCategory,
				wisdomId);

		try {
			Entity entity = mDS.get(key);

			// If target entity is null
			if (entity == null) {
				DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
				entity = helper.parseWisdomDataToEntity(wisdom, entity);
				if (entity != null) {
					mDS.put(entity);
				} else {
					DbgUtil.showLog(TAG, "Entity is not updated");
				}
			} else {
				throw new WisdomDatastoreException("Entity already exist");
			}
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			throw new WisdomDatastoreException(e.getMessage());
		}

	}

}
