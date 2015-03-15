package com.mame.wisdom.datastore;

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

	public List<WDWisdomData> getPopularWisdoms(int num) {
		DbgUtil.showLog(TAG, "getPopularWisdoms");

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO wisdomDAO = factory.getWisdomDAO();
			List<WDWisdomData> result = wisdomDAO.getPopularWisdoms(num);
			if (result == null) {
				DbgUtil.showLog(TAG, "result is null");
				return null;
			} else {
				DbgUtil.showLog(TAG, "result is not null");
				return wisdomDAO.getPopularWisdoms(num);
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
			List<WDWisdomData> result = wisdomDAO.getPopularWisdoms(num);
			if (result == null) {
				DbgUtil.showLog(TAG, "result is null");
				return null;
			} else {
				DbgUtil.showLog(TAG, "result is not null");
				return wisdomDAO.getPopularWisdoms(num);
			}

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	public WDWisdomData getWisdomById(String category, String subCategory,
			long wisdomId) {
		DbgUtil.showLog(TAG, "getWisdomById");
		// TODO

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

	public List<WDSubCategoryData> getCategoryContent() {
		DbgUtil.showLog(TAG, "getCategoryContent");

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}
		return null;
	}

}
