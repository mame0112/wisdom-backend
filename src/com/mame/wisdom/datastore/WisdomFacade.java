package com.mame.wisdom.datastore;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.mame.wisdom.constant.WConstant;
import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.data.WDWisdomItemEntry;
import com.mame.wisdom.data.WisdomDataStructure;
import com.mame.wisdom.exception.WisdomDatastoreException;
import com.mame.wisdom.exception.WisdomFacadeException;
import com.mame.wisdom.search.WisdomSearchService;
import com.mame.wisdom.util.DbgUtil;
import com.mame.wisdom.util.UserPointOption;

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
			return wisdomDAO.getPopularWisdoms(offset, limit);

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
			return wisdomDAO.getLatestWisdoms(num);

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return null;
	}

	public List<WDWisdomData> getWisdomByIds(List<Long> wisdomIds)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getWisdomByIds");

		if (wisdomIds == null) {
			throw new WisdomFacadeException("wisdomIds is null");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			List<WDWisdomData> result = dao.getWisdomsByIds(wisdomIds);
			return result;
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
			throw new WisdomFacadeException(e.getMessage());
		}

	}

	public WDWisdomData getWisdomById(long wisdomId)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getWisdomById");
		if (wisdomId == WConstant.NO_WISDOM) {
			throw new WisdomFacadeException("illegal parameter");
		}

		DAOFactory factory = DAOFactory.getDAOFactory();
		try {
			WisdomDAO dao = factory.getWisdomDAO();
			WDWisdomData result = dao.getWisdom(wisdomId);
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

	public WDWisdomData createNewWisdom(String input, Blob thumbnail)
			throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "createNewWisdom");
		if (input != null) {
			WisdomDAO facade = new DefaultWisdomDAO();
			WisdomFacadeHelper helper = new WisdomFacadeHelper();

			try {
				WisdomDataStructure data = helper
						.createWisdomDataFromInputString(input);
				data.getWisdomData().setThumbnail(thumbnail);
				return facade.addWisdom(data.getCategory(),
						data.getSubCategory(),
						assignIndexAndOrder(data.getWisdomData()));
			} catch (WisdomDatastoreException e) {
				DbgUtil.showLog(TAG,
						"WisdomDatastoreException: " + e.getMessage());
				throw new WisdomFacadeException(e.getMessage());
			}
		}

		return null;
	}

	/**
	 * Assign id for each entry and define order for them.
	 * 
	 * @param data
	 * @return
	 */
	private WDWisdomData assignIndexAndOrder(WDWisdomData data) {
		DbgUtil.showLog(TAG, "assignIndexAndOrder");
		if (data != null && data.getItems() != null) {
			List<WDWisdomItemEntry> items = data.getItems();

			int count = 0;

			for (WDWisdomItemEntry item : items) {
				item.setItemId(count);
				count = count + 1;
			}
		}
		return data;
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

	public List<WDWisdomData> getUserCreatedData(long userId, int offset,
			int limit) throws WisdomFacadeException {
		DbgUtil.showLog(TAG, "getUserCreatedData");

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

	public boolean updateWisdom(WDWisdomData data) {
		DbgUtil.showLog(TAG, "updateWisdom");

		if (data == null) {
			throw new IllegalArgumentException("parameter is null");
		}

		if (data.getWisdomId() == WConstant.NO_WISDOM) {
			throw new IllegalArgumentException("Illegal wisdom id");
		}

		try {

			WisdomSearchService service = new WisdomSearchService();
			service.addValue(data);

			DAOFactory factory = DAOFactory.getDAOFactory();
			WisdomDAO dao = factory.getWisdomDAO();
			return dao.updateWisdom(data);
		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return false;
	}

	public boolean updateMessageLikeNum(long wisdomId, long itemId) {
		DbgUtil.showLog(TAG, "updateMessageLikeNum");

		if (wisdomId == WConstant.NO_WISDOM || itemId == WConstant.NO_WISDOM) {
			DbgUtil.showLog(TAG, "wisdomId: " + wisdomId + " itemId " + itemId);
			throw new IllegalArgumentException("Illegal parameter");
		}

		try {
			DAOFactory factory = DAOFactory.getDAOFactory();
			WisdomDAO dao = factory.getWisdomDAO();
			WDWisdomData data = dao.getWisdom(wisdomId);
			List<WDWisdomItemEntry> items = data.getItems();

			boolean isUpdated = false;

			for (WDWisdomItemEntry entry : items) {
				if (entry.getItemId() == itemId) {
					int likeNum = entry.getNumberOfLike();
					likeNum = likeNum + 1;
					entry.setItemLikeNum(likeNum);
					isUpdated = true;
					break;
				}
			}

			if (isUpdated) {
				dao.updateWisdom(data);
			}
			return true;

		} catch (WisdomDatastoreException e) {
			DbgUtil.showLog(TAG, "WisdomDatastoreException: " + e.getMessage());
		}

		return false;
	}
}
