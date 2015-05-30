package com.mame.wisdom.datastore;

import java.util.List;
import java.util.Map;

import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.data.WDUserStatusData;
import com.mame.wisdom.exception.WisdomDatastoreException;

public interface UserDAO {

	public void createAllUserDataIfNecessary();

	public long getCurrentTotalUserNumber() throws WisdomDatastoreException;

	public WDUserData findUserDataByTwitterAccount(String twitterName)
			throws WisdomDatastoreException;

	public long storeNewUserData(WDUserData data)
			throws WisdomDatastoreException;

	/**
	 * Return user data. This API should return core user data (user name and
	 * thumbnail etc.)
	 * 
	 * @param userId
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public WDUserData getUserData(long userId) throws WisdomDatastoreException;

	public WDUserStatusData getUserStatusData(long userId)
			throws WisdomDatastoreException;

	public void updateUserData(WDUserData data) throws WisdomDatastoreException;

	/**
	 * Update user status
	 * 
	 * @param userId
	 * @param updatePoint
	 * @return updated userpoint
	 * @throws WisdomDatastoreException
	 */
	public long updateUserStatus(long userId, long updatePoint,
			long createdWisdomId, long likedWisdomId)
			throws WisdomDatastoreException;

	/**
	 * Get highest user list based on point
	 * 
	 * @param limit
	 * @throws WisdomDatastoreException
	 */
	public List<WDUserStatusData> getHighestPointUserList(int limit)
			throws WisdomDatastoreException;

	/**
	 * Get WDUserData by user name
	 * 
	 * @param userName
	 * @return
	 * @throws WisdomDatastoreException
	 */
	public WDUserData getUserIdByUserName(String userName)
			throws WisdomDatastoreException;

	public Map<Long, WDUserData> getUserDataList(List<WDUserStatusData> data)
			throws WisdomDatastoreException;

	public WDUserData getUserDataByFacebookName(String facebookName)
			throws WisdomDatastoreException;

}
