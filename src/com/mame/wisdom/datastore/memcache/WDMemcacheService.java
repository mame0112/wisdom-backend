package com.mame.wisdom.datastore.memcache;

import com.mame.wisdom.exception.MemcacheException;

public interface WDMemcacheService {

	public void setCache(Object param) throws MemcacheException;

	public Object getCache() throws MemcacheException;

	public void clearCache() throws MemcacheException;

}
