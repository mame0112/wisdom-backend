package com.mame.wisdom.data;


public interface WDWisdomItemEntry {

	public long getItemId();

	public String getItem();

	/**
	 * Return identifier whether the target entity is message or title. (Since
	 * this would be used on UI Layer)
	 * 
	 * @return tag identifier.
	 */
	public int getTag();

	public int getNumberOfLike();
	
	public long getLastUpdateUserId();

	public String getLastUpdateUserName();

}
