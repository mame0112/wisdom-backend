package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDSubCategoryKeyData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.datastore.memcache.LatestWisdomMemcacheService;
import com.mame.wisdom.datastore.memcache.PopularWisdomMemcacheService;
import com.mame.wisdom.datastore.memcache.WDMemcacheManager;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.search.WisdomSearchService;
import com.mame.wisdom.util.DbgUtil;

public class DefaultWisdomDAO implements WisdomDAO {

	private final static String TAG = DefaultWisdomDAO.class.getSimpleName();

	private final static DatastoreService mDS = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public void createAllWisdomDataIfNecessary() {
		DbgUtil.showLog(TAG, "createAllWisdomDataIfNecessary");
		Key key = DatastoreKeyGenerator.getAllWisdomDataKey();
		try {
			Entity entity = mDS.get(key);
			long num = (Long) entity
					.getProperty(DBConstant.ENTITY_TOTAL_WISDOM_NUMBER);
			DbgUtil.showLog(TAG, "total wisdom Number: " + num);
			if (num == -1) {
				entity.setProperty(DBConstant.ENTITY_TOTAL_WISDOM_NUMBER, 0);
				mDS.put(entity);
			}
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
			Entity entity = new Entity(key);
			entity.setProperty(DBConstant.ENTITY_TOTAL_WISDOM_NUMBER, 0);
			mDS.put(entity);
		}
	}

	@Override
	public List<WDWisdomData> getPopularWisdoms(int offset, int limit)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		WDMemcacheManager memManager = new WDMemcacheManager(
				(new PopularWisdomMemcacheService()));
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

		// If memcache doesn't exist
		if (result == null) {
			DbgUtil.showLog(TAG, "memcache doesn't exist");

			// First, count the number of wisdom
			Key allWisdomKey = DatastoreKeyGenerator.getAllWisdomDataKey();
			Entity allWisdomEntity;

			long divideNum = 1;

			try {
				allWisdomEntity = mDS.get(allWisdomKey);
				long totalWisdomNum = (long) allWisdomEntity
						.getProperty(DBConstant.ENTITY_TOTAL_WISDOM_NUMBER);
				divideNum = (long) (totalWisdomNum / WConstant.HIGHLIGHT_WISDOM_NUM);
				DbgUtil.showLog(TAG, "divideNum: " + divideNum);
			} catch (EntityNotFoundException e1) {
				DbgUtil.showLog(TAG,
						"EntityNotFoundException: " + e1.getMessage());
			}

			List<Entity> entities = new ArrayList<Entity>();

			for (int i = 0; i < WConstant.HIGHLIGHT_WISDOM_NUM; i++) {
				try {
					long targetWisdomId = i * divideNum;
					Key wisdomKey = DatastoreKeyGenerator
							.getWisdomKeyById(targetWisdomId);
					Entity wisdomEntity = mDS.get(wisdomKey);
					DbgUtil.showLog(
							TAG,
							"result title:"
									+ wisdomEntity
											.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
					entities.add(wisdomEntity);

				} catch (EntityNotFoundException e) {
					DbgUtil.showLog(TAG,
							"EntityNotFoundException: " + e.getMessage());
				} catch (IllegalArgumentException e) {
					DbgUtil.showLog(TAG,
							"IllegalArgumentException: " + e.getMessage());
				} catch (DatastoreFailureException e) {
					DbgUtil.showLog(TAG,
							"DatastoreFailureException: " + e.getMessage());
				}
			}

			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
			result = helper.parseListEntityToWDWisdomData(entities);

			// Set memcache
			if (result != null) {
				memManager.setCache(result);
			}

			return result;

			// Query q = new Query(DBConstant.KIND_WISDOM);
			// PreparedQuery pq = mDS.prepare(q);

			// Filter popularFilter = new FilterPredicate(
			// DBConstant.ENTITY_WISDOM_LAST_UPDATED_DATE,
			// FilterOperator.EQUAL, wisdomId);

			// for (Entity entity : pq.asIterable()) {
			// DbgUtil.showLog(
			// TAG,
			// "result title:"
			// + entity.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
			// }
			//
			// try {
			// FetchOptions fetch = FetchOptions.Builder.withOffset(offset)
			// .limit(limit);
			// List<Entity> entities = pq.asList(fetch);
			// DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
			// result = helper.parseListEntityToWDWisdomData(entities);
			//
			// // Set memcache
			// if (result != null) {
			// memManager.setCache(result);
			// }
			//
			// return result;
			//
			// } catch (IllegalStateException e) {
			// DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
			// throw new WisdomDatastoreException(e.getMessage());
			// }
		} else {
			DbgUtil.showLog(TAG, "memcache already exist");
			return result;
		}
	}

	@Override
	public List<WDWisdomData> getLatestWisdoms(int num)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getLatestWisdoms");

		WDMemcacheManager memManager = new WDMemcacheManager(
				(new LatestWisdomMemcacheService()));
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

		// If no memcache exist
		if (result == null) {
			DbgUtil.showLog(TAG, "Memcache for getLatestWisdoms is null");
			Query q = new Query(DBConstant.KIND_WISDOM).addSort(
					DBConstant.ENTITY_WISDOM_LAST_UPDATED_DATE,
					SortDirection.DESCENDING);
			PreparedQuery pq = mDS.prepare(q);
			// for (Entity e : pq.asIterable()) {
			// DbgUtil.showLog(
			// TAG,
			// "result title:"
			// + e.getProperty(DBConstant.ENTITY_WISDOM_TITLE));
			// }

			FetchOptions fetch = FetchOptions.Builder.withOffset(0).limit(
					WConstant.HIGHLIGHT_WISDOM_NUM);
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
	public WDWisdomData getWisdom(long wisdomId)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdom");

		Key key = DatastoreKeyGenerator.getWisdomKeyById(wisdomId);
		Query query = new Query(DBConstant.KIND_WISDOM, key);
		PreparedQuery pQuery = mDS.prepare(query);
		Entity entity = pQuery.asSingleEntity();

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
		return helper.parseEntityToWDWisdomData(entity);
	}

	@Override
	public WDWisdomData addWisdom(String category, String subCategory,
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

				Key wisdomKey = DatastoreKeyGenerator.getWisdomKeyById(newId);

				// Create or update wisdom entity
				// TODO We have to consider if we try to input same tag name
				// with previous time
				createOrUpdateWisdomEntity(wisdom, wisdomKey, newId, category,
						subCategory);

				wisdom.setWisdomId(newId);

				WisdomSearchService service = new WisdomSearchService();
				service.addValue(wisdom);

				// Finish transaction with success
				tx.commit();

				return wisdom;

			} else {
				// If target sub category doesn't exist
				DbgUtil.showLog(TAG, "Entity doesn't exist");

				String keyName = DatastoreKeyGenerator.getSubCategoryKeyName(
						category, subCategory);

				// Create and put new subcategory entity
				Entity subCategoryEntity = createSubcategoryEntity(newId,
						keyName);
				mDS.put(subCategoryEntity);

				Key wisdomKey = DatastoreKeyGenerator.getWisdomKeyById(newId);

				// Create or update wisdom entity
				createOrUpdateWisdomEntity(wisdom, wisdomKey, newId, category,
						subCategory);

				wisdom.setWisdomId(newId);

				WisdomSearchService service = new WisdomSearchService();
				service.addValue(wisdom);

				// Finish transaction with success
				tx.commit();

				return wisdom;

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
			long newId, String category, String subCategory) {
		DbgUtil.showLog(TAG, "createOrUpdateWisdomEntity");

		Entity wisdomEntity = getWisdomEntity(wisdomKey);
		wisdom.setWisdomId(newId);

		DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

		// Update total wisdom num
		Key allKey = DatastoreKeyGenerator.getAllWisdomDataKey();
		Entity totalWisdomEntity;
		try {
			totalWisdomEntity = mDS.get(allKey);
			long totalNum = (long) totalWisdomEntity
					.getProperty(DBConstant.ENTITY_TOTAL_WISDOM_NUMBER);
			totalNum = totalNum + 1;
			totalWisdomEntity.setProperty(
					DBConstant.ENTITY_TOTAL_WISDOM_NUMBER, totalNum);
			mDS.put(totalWisdomEntity);
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "No all wisdom kind");
		}

		// If target wisdomEntity already exist
		if (wisdomEntity != null) {
			DbgUtil.showLog(TAG, "Wisdom already exist");
			wisdomEntity = helper.parseWisdomDataToEntity(wisdom, wisdomEntity);
			putCategoryInfoToWisdomEntity(wisdomEntity, category, subCategory);
			mDS.put(wisdomEntity);
			// wisdomEntity = updateWisdomEntity(wisdomEntity, newId);
		} else {
			DbgUtil.showLog(TAG, "Wisdom doesn't exist");
			Entity newWisdomEntity = new Entity(wisdomKey);
			newWisdomEntity = helper.parseWisdomDataToEntity(wisdom,
					newWisdomEntity);
			putCategoryInfoToWisdomEntity(newWisdomEntity, category,
					subCategory);
			mDS.put(newWisdomEntity);
		}
	}

	private void putCategoryInfoToWisdomEntity(Entity wisdomEntity,
			String category, String subCategory) {
		wisdomEntity.setProperty(DBConstant.ENTITY_WISDOM_CATEGORY, category);
		wisdomEntity.setProperty(DBConstant.ENTITY_WISDOM_SUBCATEGORY,
				subCategory);

	}

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

		Key key = DatastoreKeyGenerator.getWisdomKeyById(wisdomId);
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
	public boolean updateWisdom(WDWisdomData wisdom)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "updateWisdom");

		if (wisdom == null) {
			throw new WisdomDatastoreException("parameter is null");
		}

		long wisdomId = wisdom.getWisdomId();

		if (wisdomId == WConstant.NO_WISDOM) {
			throw new WisdomDatastoreException("Illegal wisdom id");
		}

		try {
			Filter searchFilter = new FilterPredicate(
					DBConstant.ENTITY_WISDOM_ID, FilterOperator.EQUAL, wisdomId);
			Query q = new Query(DBConstant.KIND_WISDOM).setFilter(searchFilter);
			PreparedQuery pq = mDS.prepare(q);
			Entity entity = pq.asSingleEntity();
			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
			entity = helper.parseWisdomDataToEntity(wisdom, entity);

			if (entity != null) {
				mDS.put(entity);
				return true;
			}
		} catch (TooManyResultsException e) {
			DbgUtil.showLog(TAG, "TooManyResultsException: " + e.getMessage());
		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		}

		return false;

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

			DbgUtil.showLog(TAG, "searchParam: " + searchParam);

			WisdomSearchService service = new WisdomSearchService();
			List<WDSubCategoryKeyData> wisdoms = service
					.searchWisdomByParameter(searchParam);

			List<WDWisdomData> result = new ArrayList<WDWisdomData>();

			for (WDSubCategoryKeyData wisdom : wisdoms) {

				try {
					Key key = DatastoreKeyGenerator.getWisdomKeyById(wisdom
							.getWisdomId());

					Entity entity = mDS.get(key);
					DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

					WDWisdomData data = helper
							.parseEntityToWDWisdomData(entity);

					result.add(data);

				} catch (TooManyResultsException e) {
					DbgUtil.showLog(TAG,
							"TooManyResultsException: " + e.getMessage());
				} catch (IllegalStateException e) {
					DbgUtil.showLog(TAG,
							"IllegalStateException: " + e.getMessage());
				} catch (EntityNotFoundException e) {
					DbgUtil.showLog(TAG,
							"EntityNotFoundException: " + e.getMessage());
				}
			}

			return result;
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
	public List<WDWisdomData> getWisdomsByIds(List<Long> wisdomIds)
			throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getWisdomsByIds");

		if (wisdomIds == null) {
			throw new WisdomDatastoreException("Parameter is null");
		}

		if (wisdomIds != null) {

			List<WDWisdomData> result = new ArrayList<WDWisdomData>();
			DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

			for (long wisdomId : wisdomIds) {
				Key key = DatastoreKeyGenerator.getWisdomKeyById(wisdomId);
				try {
					Entity entity = mDS.get(key);
					WDWisdomData data = helper
							.parseEntityToWDWisdomData(entity);
					result.add(data);
				} catch (EntityNotFoundException e) {
					DbgUtil.showLog(TAG,
							"EntityNotFoundException:: " + e.getMessage());
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

		try {
			if (wisdomId != WConstant.NO_WISDOM) {
				Key key = DatastoreKeyGenerator.getWisdomKeyById(wisdomId);
				Entity entity = mDS.get(key);

				// If target Entity exist
				if (entity != null) {
					DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();

					WDWisdomData data = helper
							.parseEntityToWDWisdomData(entity);

					// Increate view count
					data.increaseViewCount();

					// Get new view count and store it onto Datastroe
					entity.setProperty(DBConstant.ENTITY_WISDOM_VIEWED_COUNT,
							data.getViewCount());

					// View count is not so critical information, then we don't
					// update
					// memcache for this timing.
					mDS.put(entity);

					return data;
				} else {
					DbgUtil.showLog(TAG,
							"Target wisdom doesn't exist. widomId: " + wisdomId);
				}

			}
		} catch (TooManyResultsException e) {
			DbgUtil.showLog(TAG, "TooManyResultsException: " + e.getMessage());
		} catch (IllegalStateException e) {
			DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		return null;
	}

	@Override
	public List<WDWisdomData> getUserLikedWisdoms(long userId, int offset,
			int limit) throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "getUserLikedWisdoms");

		Key key = DatastoreKeyGenerator.getUserStatusKey(userId);
		try {
			Entity entity = mDS.get(key);
			List<Long> likedWisdomIds = (List<Long>) entity
					.getProperty(DBConstant.ENTITY_STATUS_LIKED_WISDOM);

			// TODO
			// DatastoreKeyGenerator.get

		} catch (EntityNotFoundException e) {
			DbgUtil.showLog(TAG, "EntityNotFoundException: " + e.getMessage());
		}

		return null;

		// Filter searchFilter = new FilterPredicate(
		// DBConstant.ENTITY_WISDOM_CREATED_USER_ID, FilterOperator.EQUAL,
		// userId);
		// Query q = new Query(DBConstant.KIND_WISDOM).setFilter(searchFilter);
		// PreparedQuery pq = mDS.prepare(q);
		//
		// try {
		// FetchOptions fetch = FetchOptions.Builder.withOffset(offset).limit(
		// limit);
		// List<Entity> wisdoms = pq.asList(fetch);
		// DefaultWisdomDAOHelper helper = new DefaultWisdomDAOHelper();
		//
		// return helper.parseListEntityToWDWisdomData(wisdoms);
		//
		// } catch (IllegalStateException e) {
		// DbgUtil.showLog(TAG, "IllegalStateException: " + e.getMessage());
		// throw new WisdomDatastoreException("IllegalStateException: "
		// + e.getMessage());
		// }

	}

	@Override
	public void refreshOldMemcacheData() throws WisdomDatastoreException {
		DbgUtil.showLog(TAG, "refreshOldMemcacheData");

		WDMemcacheManager memManager = new WDMemcacheManager(
				(new LatestWisdomMemcacheService()));
		List<WDWisdomData> result = (List<WDWisdomData>) memManager.getCache();

	}
}
