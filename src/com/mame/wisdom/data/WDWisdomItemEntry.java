package com.mame.wisdom.data;

import java.io.Serializable;

public interface WDWisdomItemEntry extends Serializable {

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

	public long getLastUpdateDate();
	
	public void setItemId(long id);

}
