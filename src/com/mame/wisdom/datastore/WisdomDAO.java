package com.mame.wisdom.datastore;

import java.util.List;

import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.WisdomDatastoreException;

public interface WisdomDAO {

	public List<WDWisdomData> getPopularWisdoms();

	/**
	 * This would be used if the user selects category and sub category on UI.
	 * 
	 * @param category
	 * @param subCategory
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public List<WDWisdomData> getWisdoms(String category, String subCategory)
			throws WisdomDatastoreException;

	/**
	 * This would be used if the user selects one of wisdom from wisdom list.
	 * 
	 * @param category
	 * @param subCategory
	 * @param wisdomId
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public WDWisdomData getWisdom(String category, String subCategory,
			long wisdomId) throws WisdomDatastoreException;

	/**
	 * This would be used if the user wants to create new wisdom
	 * 
	 * @param category
	 * @param subCategory
	 * @param wisdom
	 * @throws WisdomDatastoreException
	 */
	public void addWisdom(String category, String subCategory,
			WDWisdomData wisdom) throws WisdomDatastoreException;

}
