package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.memcache.PopularWisdomMemcacheService;
import com.mame.wisdom.datastore.memcache.WDMemcacheManager;
import com.mame.wisdom.datastore.memcache.WDMemcacheService;
import com.mame.wisdom.datastore.memcache.LatestWisdomMemcacheService;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.util.DbgUtil;

public class DefaultWisdomDAO implements WisdomDAO {

	private final static String TAG = DefaultWisdomDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public List<WDWisdomData> getPopularWisdoms(int offset, int limit)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		WDMemcacheManager memManager = WDMemcacheManager
				.getInstance(new PopularWisdomMemcacheService());
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

		// If memcache doesn't exist
		if (result == null) {
			DbgUtil.showLog(TAG, "memcache doesn't exist");
			Query q = new Query(DBConstant.KIND_WISDOM);
			PreparedQuery pq = mDS.prepare(q);

			for (Entity entity : pq.asIterable()) {
				DbgUtil.showLog(
						TAG,
						"result title:"
								+ entity.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
			}

			try {
				FetchOptions fetch = FetchOptions.Builder.withOffset(offset)
						.limit(limit);
				List<Entity> entities = pq.asList(fetch);
				DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

				result = helper.parseListEntityToWDWisdomData(entities);

				// Set memcache
				if (result != null) {
					memManager.setCache(result);
				}

				return result;

			} catch (IllegalStateException e) {
				DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
				throw new WisdomDatastoreException(e.getMessage());
			}
		} else {
			DbgUtil.showLog(TAG, "memcache already exist");
			return result;
		}
	}

	@Override
	public List<WDWisdomData> getLatestWisdoms(int num)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getLatestWisdoms");

		WDMemcacheManager memManager = WDMemcacheManager
				.getInstance(new LatestWisdomMemcacheService());
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

		// If no memcache exist
		if (result == null) {
			DbgUtil.showLog(TAG, "Memcache for getLatestWisdoms is null");
			Query q = new Query(DBConstant.KIND_WISDOM);
			PreparedQuery pq = mDS.prepare(q);
			for (Entity e : pq.asIterable()) {
				DbgUtil.showLog(
						TAG,
						"result title:"
								+ e.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
			}

			FetchOptions fetch = FetchOptions.Builder.withOffset(0).limit(10);
			List<Entity> entities = pq.asList(fetch);

			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

			result = helper.parseListEntityToWDWisdomData(entities);

			// Set data to memcache
			if (result != null) {
				memManager.setCache(result);
			}

		} else {
			DbgUtil.showLog(TAG, "Memcache for getLatestWisdoms already exists");
		}

		return result;
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

		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		Transaction tx = mDS.beginTransaction(options);

		Key key = DatastoreKeyGenerator
				.getSubCategoryKey(category, subCategory);

		// Check subcategory kind
		try {

			long newId = getTotalWisdomNum() + 1;

			Entity entity = getSubCategoryEntity(key);

			// If target sub category entity already exist
			if (entity != null) {
				DbgUtil.showLog(TAG, "Entity already exist");

				// Update subcategory entity
				entity = updateSubcategoryEntity(entity, newId);

				// Put sub category entity
				mDS.put(entity);

				Key wisdomKey = DatastoreKeyGenerator.getWisdomKeyById(
						category, subCategory, newId);

				// Create or update wisdom entity
				// TODO We have to consider if we try to input same tag name
				// with previous time
				createOrUpdateWisdomEntity(wisdom, wisdomKey, newId);

				// Finish transaction with success
				tx.commit();

			} else {
				// If target sub category doesn't exist
				DbgUtil.showLog(TAG, "Entity doesn't exist");

				String keyName = DatastoreKeyGenerator.getSubCategoryKeyName(
						category, subCategory);

				// Create and put new subcategory entity
				Entity subCategoryEntity = createSubcategoryEntity(newId,
						keyName);
				mDS.put(subCategoryEntity);

				Key wisdomKey = DatastoreKeyGenerator.getWisdomKeyById(
						category, subCategory, newId);

				// Create or update wisdom entity
				createOrUpdateWisdomEntity(wisdom, wisdomKey, newId);

				// Finish transaction with success
				tx.commit();

			}
		} catch (ConcurrentModificationException e) {
			DbgUtil.showLog(TAG, "ConcurrentModificationException");
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new WisdomDatastoreException(e.getMessage());
		}
	}

	private Entity createSubcategoryEntity(long newId, String keyName) {
		DbgUtil.showLog(TAG, "createSubcategoryEntity");

		Entity newSubCategoryEntity = new Entity(DBConstant.KIND_SUB_CATEGORY,
				keyName);
		List<Long> ids = new ArrayList<Long>();
		ids.add(newId);

		newSubCategoryEntity.setProperty(DBConstant.ENTITY_CATEGORY_WISDOM_IDS,
				ids);
		return newSubCategoryEntity;
	}

	private Entity updateSubcategoryEntity(Entity entity, long newId) {
		DbgUtil.showLog(TAG, "updateSubcategoryEntity");

		List<Long> wisdomIds = (List<Long>) entity
				.getProperty(DBConstant.ENTITY_CATEGORY_WISDOM_IDS);
		if (wisdomIds != null) {
			wisdomIds.add(newId);
			entity.setProperty(DBConstant.ENTITY_CATEGORY_WISDOM_IDS, wisdomIds);
		} else {
			List<Long> ids = new ArrayList<Long>();
			ids.add(newId);
			entity.setProperty(DBConstant.ENTITY_CATEGORY_WISDOM_IDS, ids);
		}

		return entity;
	}

	private void createOrUpdateWisdomEntity(WDWisdomData wisdom, Key wisdomKey,
			long newId) {
		DbgUtil.showLog(TAG, "createOrUpdateWisdomEntity");

		Entity wisdomEntity = getWisdomEntity(wisdomKey);
		wisdom.setWisdomId(newId);

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

		// If target wisdomEntity already exist
		if (wisdomEntity != null) {
			DbgUtil.showLog(TAG, "Wisdom already exist");
			wisdomEntity = helper.parseWisdomDataToEntity(wisdom, wisdomEntity);
			mDS.put(wisdomEntity);
			// wisdomEntity = updateWisdomEntity(wisdomEntity, newId);
		} else {
			DbgUtil.showLog(TAG, "Wisdom doesn't exist");
			Entity newWisdomEntity = new Entity(wisdomKey);
			newWisdomEntity = helper.parseWisdomDataToEntity(wisdom,
					newWisdomEntity);
			mDS.put(newWisdomEntity);
		}
	}

	// private long getTotalWisdomNum(Key ancestrKey) {
	// DbgUtil.showLog(TAG, "getTotalWisdomNum");
	// Query query = new Query(DBConstant.KIND_WISDOM, ancestrKey);
	// long wisdomNum = mDS.prepare(query).countEntities(
	// FetchOptions.Builder.withDefaults());
	// DbgUtil.showLog(TAG, "wisdomNum: " + wisdomNum);
	// return wisdomNum;
	// }

	private long getTotalWisdomNum() {
		DbgUtil.showLog(TAG, "getTotalWisdomNum");
		Query query = new Query(DBConstant.KIND_WISDOM);
		long wisdomNum = mDS.prepare(query).countEntities(
				FetchOptions.Builder.withDefaults());
		DbgUtil.showLog(TAG, "wisdomNum: " + wisdomNum);
		return wisdomNum;
	}

	private Entity getSubCategoryEntity(Key subCategoryKey) {
		DbgUtil.showLog(TAG, "getSubCategoryEntity");
		try {
			DbgUtil.showLog(TAG, "getSubCategoryEntity: " + subCategoryKey);
			return mDS.get(subCategoryKey);
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			return null;
		}
	}

	private Entity getWisdomEntity(Key wisdomKey) {
		DbgUtil.showLog(TAG, "getWisdomEntity");
		try {
			return mDS.get(wisdomKey);
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			return null;
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
			try {
				DbgUtil.showLog(TAG, "searchParam: " + searchParam);
				// Need to check if this work

				Filter searchFilter = new FilterPredicate(
						DBConstant.ENTITY_WISDOM_DESCRIPTION,
						FilterOperator.IN, searchParam);
				// Filter searchFilter = new FilterPredicate(
				// DBConstant.ENTITY_WISDOM_DESCRIPTION,
				// FilterOperator.EQUAL, searchParam);
				Query q = new Query(DBConstant.KIND_WISDOM)
						.setFilter(searchFilter);
				PreparedQuery pq = mDS.prepare(q);
				for (Entity result : pq.asIterable()) {
					DbgUtil.showLog(
							TAG,
							"result title:"
									+ result.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
				}

				FetchOptions fetch = FetchOptions.Builder.withOffset(offset)
						.limit(limit);

				List<Entity> entities = pq.asList(fetch);
				if (entities != null) {
					DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
					return helper.parseListEntityToWDWisdomData(entities);
				}
			} catch (IllegalStateException e) {
				DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
				throw new WisdomDatastoreException(e.getMessage());
			} catch (IllegalArgumentException e) {
				DbgUtil.showLog(TAG,
						"IllegalArgumentException: " + e.getMessage());
				throw new WisdomDatastoreException(e.getMessage());
			}

		}

		return null;
	}

	@Override
	public WDSubCategoryData getCategoryContents(String categoryName,
			String subCategoryName) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getCategoryContents");

		if (categoryName == null || subCategoryName == null) {
			throw new WisdomDatastoreException(
					"categoryName or subCategoryName is null");
		}

		Key key = DatastoreKeyGenerator.getSubCategoryKey(categoryName,
				subCategoryName);
		try {
			Entity entity = mDS.get(key);
			// If entity already exist
			if (entity != null) {
				String description = (String) entity
						.getProperty(DBConstant.ENTITY_CATEGORY_DESCRIPTION);
				List<Long> wisdomIds = (List<Long>) entity
						.getProperty(DBConstant.ENTITY_CATEGORY_WISDOM_IDS);

				long totalNum = 0;
				if (wisdomIds != null) {
					totalNum = wisdomIds.size();
				}

				// TODO Need to consider offset and limit handling
				WDSubCategoryData data = new WDSubCategoryData(0, 0,
						categoryName, subCategoryName, description, wisdomIds,
						totalNum, 0, wisdomIds.size());
				return data;
			}
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		return null;

	}

	@Override
	public List<WDWisdomData> getWisdomsByIds(String category,
			String subCategory, List<Long> wisdomIds)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdomsByIds");

		if (category == null || subCategory == null) {
			throw new WisdomDatastoreException(
					"category or subCategory name is null");
		}

		if (wisdomIds != null) {

			List<WDWisdomData> result = new ArrayList<WDWisdomData>();
			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

			for (long wisdomId : wisdomIds) {
				Key key = DatastoreKeyGenerator.getWisdomKeyById(category,
						subCategory, wisdomId);
				try {
					Entity entity = mDS.get(key);
					WDWisdomData data = helper
							.parseEntityToWDWisdomData(entity);
					result.add(data);
				} catch (EntityNotFoundException e) {
					DbgUtil.showLog(TAG,
							"EntityNotFoundException: " + e.getMessage());
					throw new WisdomDatastoreException(e.getMessage());
				}
			}

			return result;
		}

		return null;
	}

	@Override
	public WDWisdomData findWisdomById(long wisdomId)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "findWisdomById");

		Filter searchFilter = new FilterPredicate(DBConstant.ENTITY_WISDOM_ID,
				FilterOperator.EQUAL, wisdomId);
		Query q = new Query(DBConstant.KIND_WISDOM).setFilter(searchFilter);
		PreparedQuery pq = mDS.prepare(q);
		try {
			Entity entity = pq.asSingleEntity();
			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

			return helper.parseEntityToWDWisdomData(entity);

		} catch (TooManyResultsException e) {
			DbgUtil.showLog(TAG, "TooManyResultsException: " + e.getMessage());
		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		}

		return null;
	}

	@Override
	public List<WDWisdomData> getUserLikedWisdoms(long userId, int offset,
			int limit) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserLikedWisdoms");

		Filter searchFilter = new FilterPredicate(
				DBConstant.ENTITY_WISDOM_CREATED_USER_ID, FilterOperator.EQUAL,
				userId);
		Query q = new Query(DBConstant.KIND_WISDOM).setFilter(searchFilter);
		PreparedQuery pq = mDS.prepare(q);

		try {
			FetchOptions fetch = FetchOptions.Builder.withOffset(offset).limit(
					limit);
			List<Entity> wisdoms = pq.asList(fetch);
			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

			return helper.parseListEntityToWDWisdomData(wisdoms);

		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
			throw new WisdomDatastoreException("IllegalStateException: "
					+ e.getMessage());
		}

	}

	@Override
	public void refreshOldMemcacheData() throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "refreshOldMemcacheData");

		WDMemcacheManager memManager = WDMemcacheManager
				.getInstance(new LatestWisdomMemcacheService());
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

	}
}
