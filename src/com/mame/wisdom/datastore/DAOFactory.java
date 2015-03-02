package com.mame.wisdom.datastore;

import com.mame.wisdom.exception.WisdomDatastoreException;

public abstract class DAOFactory {

	public static final int DEFAULT = 1;

	public static final int CIPHER = 2;

	public abstract UserDAO getUserDAO() throws WisdomDatastoreException;

	public abstract UserDAO getWisdomDAO() throws WisdomDatastoreException;

	// TODO need to consider if Notification DAO is necessary

	public static DAOFactory getDAOFactory(int selectFactory) {
		switch (selectFactory) {
		case DEFAULT:
			return new DefaultDAOFactory();
		case CIPHER:
			return new CipherDAOFactory();
		default:
			return null;
		}
	}

}
