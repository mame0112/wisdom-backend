package com.mame.wisdom.datastore;

import java.util.List;

import com.mame.wisdom.data.WDUserData;
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

	public void updateUserData(WDUserData data) throws WisdomDatastoreException;

	/**
	 * Get highest user list based on point
	 * 
	 * @param limit
	 * @throws WisdomDatastoreException
	 */
	public List<WDUserData> getHighestPointUserList(int limit)
			throws WisdomDatastoreException;

	/**
	 * Get user id for user name
	 * 
	 * @param userName
	 * @return userId for given user name.. otherwise -1 (No user id).
	 * @throws WisdomDatastoreException
	 */
	public long findUserIdByUserName(String userName)
			throws WisdomDatastoreException;

}
