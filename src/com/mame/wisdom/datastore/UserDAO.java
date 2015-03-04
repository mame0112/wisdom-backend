package com.mame.wisdom.datastore;

import com.mame.wisdom.data.WDUserData;
import com.mame.wisdom.exception.WisdomDatastoreException;

public interface UserDAO {

	public void createAllUserDataIfNecessary();
	
	public long getCurrentTotalUserNumber() throws WisdomDatastoreException;

	public WDUserData findUserDataByTwitterAccount(String twitterName)
			throws WisdomDatastoreException;

	public void storeNewUserData(WDUserData data)
			throws WisdomDatastoreException;

}
