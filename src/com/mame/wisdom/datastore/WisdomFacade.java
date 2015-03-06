package com.mame.wisdom.datastore;

import java.util.List;

import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.WisdomDatastoreException;
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

	public WDWisdomData getWisdomById(String category, String subCategory,
			long wisdomId) {
		DbgUtil.showLog(TAG, "getWisdomById");
		// TODO

		return null;
	}

}
