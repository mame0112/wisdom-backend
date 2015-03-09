package com.mame.wisdom.datastore;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DefaultWisdomDAO implements WisdomDAO {

	private final static String TAG = DefaultWisdomDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public List<WDWisdomData> getPopularWisdoms(int num)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		Query q = new Query(DBConstant.KIND_WISDOM);
		PreparedQuery pq = mDS.prepare(q);
		for (Entity result : pq.asIterable()) {
			DbgUtil.showLog(
					TAG,
					"result title:"
							+ result.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
		}

		FetchOptions fetch = FetchOptions.Builder.withOffset(0).limit(10);
		List<Entity> entities = pq.asList(fetch);

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

		return helper.parseListEntityToWDWisdomData(entities);
	}

	@Override
	public List<WDWisdomData> getLatestWisdoms(int num)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getLatestWisdoms");

		Query q = new Query(DBConstant.KIND_WISDOM);
		PreparedQuery pq = mDS.prepare(q);
		for (Entity result : pq.asIterable()) {
			DbgUtil.showLog(
					TAG,
					"result title:"
							+ result.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
		}

		FetchOptions fetch = FetchOptions.Builder.withOffset(0).limit(10);
		List<Entity> entities = pq.asList(fetch);

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

		return helper.parseListEntityToWDWisdomData(entities);
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

		Key key = DatastoreKeyGenerator
				.getSubCategoryKey(category, subCategory);
		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

		// Get entity for target category / sub cateogry
		try {
			Entity entity = mDS.get(key);
			// If target entity is not null, which mean there are more than
			// 1
			// wisdoms for target category.
			if (entity != null) {
				DbgUtil.showLog(TAG, "Entity already exist");
				int num = (int) entity
						.getProperty(DBConstant.ENTITY_WISDOM_NUM);
				wisdomId = num + 1;
				wisdom.setWisdomId(wisdomId);
				entity = helper.parseWisdomDataToEntity(wisdom, entity);
				mDS.put(entity);
			} else {
				DbgUtil.showLog(TAG, "Entity doesn't exist 1");
				// If target entity is null. which means there is no wisdom
				// for
				// target category / sub category yet.
				wisdom.setWisdomId(1);
				Entity newWisdomEntity = new Entity(DBConstant.KIND_WISDOM, 1,
						key);
				newWisdomEntity = helper.parseWisdomDataToEntity(wisdom,
						newWisdomEntity);
				mDS.put(newWisdomEntity);
			}
		} catch (EntityNotFoundException e) {
			// IF target entity doesn't exist, which means there is no
			// wisdom
			// for target category / sub category yet.
			DbgUtil.showLog(TAG, "Entity doesn't exist 2");
			wisdom.setWisdomId(1);
			Entity newWisdomEntity = new Entity(DBConstant.KIND_WISDOM, 1, key);
			newWisdomEntity = helper.parseWisdomDataToEntity(wisdom,
					newWisdomEntity);
			mDS.put(newWisdomEntity);
		}
	}

	@Override
	public void updateWisdom(String category, String subCategory,
			WDWisdomData wisdom) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "updateWisdom");

		if (category == null || subCategory == null || wisdom == null) {
			DbgUtil.showLog(TAG,
					"Illegal parameter. category, subcategory or wisdom is null");
			throw new WisdomDatastoreException(
					"Illegal parameter. category, subcategory or wisdom is null");
		}

		long wisdomId = wisdom.getWisdomId();

		if (wisdomId == WConstant.NO_WISDOM) {
			DbgUtil.showLog(TAG, "Illegal wisdomId");
			throw new WisdomDatastoreException("Illegal wisdomId");
		}

		Key key = DatastoreKeyGenerator.getWisdomKeyById(category, subCategory,
				wisdomId);
		try {
			Entity entity = mDS.get(key);
			if (entity != null) {
				DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
				entity = helper.parseWisdomDataToEntity(wisdom, entity);
				if (entity != null) {
					mDS.put(entity);
				}
			} else {
				DbgUtil.showLog(TAG, "Entity doesn't exist");
			}
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			// TODO need to consider if data for target wisdomId doesn't exist.
		}
	}

	@Override
	public List<WDWisdomData> searchWisdoms(String searchParam, int offset,
			int limit) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "searchWisdoms");

		if (offset <= -1 || limit <= 0) {
			throw new WisdomDatastoreException(
					"Illegal offset or limit parameter");
		}

		if (searchParam != null) {
			// Need to check if this work
			Filter searchFilter = new FilterPredicate(
					DBConstant.ENTITY_WISDOM_ITMES, FilterOperator.IN,
					searchParam);
			Query q = new Query(DBConstant.KIND_WISDOM).setFilter(searchFilter);
			PreparedQuery pq = mDS.prepare(q);
			for (Entity result : pq.asIterable()) {
				DbgUtil.showLog(
						TAG,
						"result title:"
								+ result.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
			}

			FetchOptions fetch = FetchOptions.Builder.withOffset(offset).limit(
					limit);
			List<Entity> entities = pq.asList(fetch);
			if (entities != null) {
				DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
				return helper.parseListEntityToWDWisdomData(entities);
			}
		}

		return null;
	}
}
