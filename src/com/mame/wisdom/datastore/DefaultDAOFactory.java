package com.mame.wisdom.datastore;

import com.mame.wisdom.exception.WisdomDatastoreException;

public class DefaultDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() throws WisdomDatastoreException {
		// TODO Auto-generated method stub
		return new DefaultUserDAO();
	}

	@Override
	public WisdomDAO getWisdomDAO() throws WisdomDatastoreException {
		// TODO Auto-generated method stub
		return new DefaultWisdomDAO();
	}

}
