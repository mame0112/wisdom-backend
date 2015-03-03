package com.mame.wisdom.datastore;

import com.mame.wisdom.exception.WisdomDatastoreException;

public abstract class DAOFactory {

	private static final int DEFAULT = 1;

	private static final int CIPHER = 2;
	
	public static final int DAO_MODE = DEFAULT;

	public abstract UserDAO getUserDAO() throws WisdomDatastoreException;

	public abstract WisdomDAO getWisdomDAO() throws WisdomDatastoreException;

	// TODO need to consider if Notification DAO is necessary

	public static DAOFactory getDAOFactory() {
		switch (DAO_MODE) {
		case DEFAULT:
			return new DefaultDAOFactory();
		case CIPHER:
			return new CipherDAOFactory();
		default:
			return null;
		}
	}

}
