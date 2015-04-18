package com.mame.wisdom.datastore;

import java.util.List;

import com.mame.wisdom.data.WDSubCategoryData;
import com.mame.wisdom.data.WDWisdomData;
import com.mame.wisdom.exception.WisdomDatastoreException;

public interface WisdomDAO {

	/**
	 * Get content information that belongs to selected category/subcategory
	 * 
	 * @param categoryName
	 * @param subCategoryName
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public WDSubCategoryData getCategoryContents(String categoryName,
			String subCategoryName) throws WisdomDatastoreException;

	/**
	 * Get popular wisdom
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 * @throws WisdomDatastoreException
	 */

	public List<WDWisdomData> getPopularWisdoms(int offset, int limit)
			throws WisdomDatastoreException;

	/**
	 * Get latest wisdoms
	 * 
	 * @param num
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public List<WDWisdomData> getLatestWisdoms(int num)
			throws WisdomDatastoreException;

	/**
	 * Search out wisdom by given search parameter.
	 * 
	 * @param searchParam
	 * @param offset
	 * @param limit
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public List<WDWisdomData> searchWisdoms(String searchParam, int offset,
			int limit) throws WisdomDatastoreException;

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
	 * Get wisdom datas by wisdom ids
	 * 
	 * @param wisdomIds
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public List<WDWisdomData> getWisdomsByIds(String categoryName,
			String subCategoryName, List<Long> wisdomIds)
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
	 * Find wisdom by given ID (without category and sub category informaton)
	 * 
	 * @param wisdomId
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public WDWisdomData findWisdomById(long wisdomId)
			throws WisdomDatastoreException;

	/**
	 * This would be used if the user wants to create new wisdom
	 * 
	 * @param category
	 * @param subCategory
	 * @param wisdom
	 * @return newly created wisdom.
	 * @throws WisdomDatastoreException
	 */
	public WDWisdomData addWisdom(String category, String subCategory,
			WDWisdomData wisdom) throws WisdomDatastoreException;

	/**
	 * This would be used if the user updates wisdom
	 * 
	 * @param category
	 * @param subCategory
	 * @param wisdom
	 * @throws WisdomDatastoreException
	 */
	public void updateWisdom(String category, String subCategory,
			WDWisdomData wisdom) throws WisdomDatastoreException;

	/**
	 * Get wisdoms those are liked by user.
	 * 
	 * @param userId
	 * @param offset
	 * @param limits
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public List<WDWisdomData> getUserLikedWisdoms(long userId, int offset,
			int limits) throws WisdomDatastoreException;

	/**
	 * Refresh old memcache data
	 * 
	 * @throws WisdomDatastoreException
	 */
	public void refreshOldMemcacheData() throws WisdomDatastoreException;

}
