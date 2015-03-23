package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WisdomDataStructure;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.exception.WisdomFacadeException;
import com.mame.wisdom.util.DbgUtil;

public class WisdomFacade {

	private final static String TAG = WisdomFacade.class.getSimpleName();

	public WisdomFacade() {
		DbgUtil.showLog(TAG, "WisdomFacade");
	}

	public void refreshOldWisdomData() {
		DbgUtil.showLog(TAG, "refreshOldWisdomData");

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO wisdomDAO = factory.getWisdomDAO();
			wisdomDAO.refreshOldMemcacheData();
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}
	}

	public List<WDWisdomData> getPopularWisdoms(int offset, int limit) {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO wisdomDAO = factory.getWisdomDAO();
			List<WDWisdomData> result = wisdomDAO.getPopularWisdoms(offset,
					limit);
			if (result == null) {
				DbgUtil.showLog(TAG, "result is null");
				return null;
			} else {
				DbgUtil.showLog(TAG, "result is not null");
				return wisdomDAO.getPopularWisdoms(offset, limit);
			}

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	public List<WDWisdomData> getLatestWisdoms(int num) {
		DbgUtil.showLog(TAG, "getLatestWisdoms");

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO wisdomDAO = factory.getWisdomDAO();
			List<WDWisdomData> result = wisdomDAO.getLatestWisdoms(num);

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	public List<WDWisdomData> getWisdomByIds(String category,
			String subCategory, List<Long> wisdomIds)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getWisdomByIds");

		if (wisdomIds == null) {
			throw new WisdomFacadeException("wisdomIds is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			List<WDWisdomData> result = dao.getWisdomsByIds(category,
					subCategory, wisdomIds);
			return result;
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}

	}

	public WDWisdomData getWisdomById(String category, String subCategory,
			long wisdomId) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getWisdomById");
		if (category == null || subCategory == null
				|| wisdomId == WConstant.NO_WISDOM) {
			throw new WisdomFacadeException("illegal parameter");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			WDWisdomData result = dao
					.getWisdom(category, subCategory, wisdomId);
			return result;
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}
	}

	public WDWisdomData findWisdomById(long wisdomId)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "findWisdomById");

		if (wisdomId < 0) {
			throw new WisdomFacadeException("illegal wisdom id");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			return dao.findWisdomById(wisdomId);

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	public List<WDWisdomData> searchWisdom(String searchKey)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "searchWisdom");

		if (searchKey == null) {
			throw new WisdomFacadeException("searchKey is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		WisdomDAO dao;
		try {
			dao = factory.getWisdomDAO();
			List<WDWisdomData> foundItems = dao.searchWisdoms(searchKey, 0,
					WConstant.SEARCH_LIMIT_NUM);
			if (foundItems != null) {
				DbgUtil.showLog(TAG, "size: " + foundItems.size());
				return foundItems;
			}
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}

		return null;
	}

	public void createNewWisdom(String input) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "createNewWisdom");
		if (input != null) {
			WisdomDAO facade = new DefaultWisdomDAO();
			WisdomFacadeHelper helper = new WisdomFacadeHelper();

			try {
				WisdomDataStructure data = helper
						.createWisdomDataFromInputString(input);
				facade.addWisdom(data.getCategory(), data.getSubCategory(),
						data.getWisdomData());
			} catch (WisdomDatastoreException e) {
				DbgUtil.showLog(TAG,
						"WisdomDatastoreException: " + e.getMessage());
				throw new WisdomFacadeException(e.getMessage());
			}

		}
	}

	public WDSubCategoryData getCategoryContent(String category,
			String subCategory) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getCategoryContent");

		if (category == null || subCategory == null) {
			throw new WisdomFacadeException("category or sub category is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();

			// Get target subcategory content (no wisdom info at this timing)
			return dao.getCategoryContents(category, subCategory);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}
		return null;
	}

	public List<WDWisdomData> getUserGeneratedData(long userId, int offset,
			int limit) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getUserGeneratedData");

		if (userId == WConstant.NO_USER) {
			throw new WisdomFacadeException("Illegal userId parameter");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			return dao.getUserLikedWisdoms(userId, offset, limit);

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;

	}

}
