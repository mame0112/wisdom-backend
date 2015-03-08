package com.mame.wisdom.datastore;

public class DBConstant {

	/**
	 * Datastore kinds
	 */
	public final static String KIND_ALL_USER = "kind_all_user";

	public final static String KIND_USER_DATA = "kind_user_data";

	public final static String KIND_SUB_CATEGORY = "kind_sub_category";

	public final static String KIND_WISDOM = "kind_wisdom";

	public final static String KIND_UPDATE_INFO = "kind_update_info";

	/**
	 * Entity fields for all user data
	 */
	public final static String ENTITY_TOTAL_USER_NUMBER = "total_user_number";

	/**
	 * Entity fields for User data
	 */
	public final static String ENTITY_USER_ID = "user_id";

	public final static String ENTITY_USER_TWITTER_NAME = "user_twitter_name";

	public final static String ENTITY_USER_TWITTER_TOKEN = "user_twitter_token";

	public final static String ENTITY_USER_TWITTER_TOKEN_SECRET = "user_twitter_token_secret";

	public final static String ENTITY_USER_FACEBOOK_NAME = "user_facebook_name";

	public final static String ENTITY_USER_NAME = "user_name";

	public final static String ENTITY_USER_PASSWORD = "user_password";

	public final static String ENTITY_USER_THUMBNAIL = "user_thumbnail";

	public final static String ENTITY_USER_LAST_LOGIN = "user_last_login";

	public final static String ENTITY_USER_TOTAL_POINT = "user_total_point";

	/**
	 * Entity fields for Wisdom data
	 */
	// This is a field for the number of wisdom which belong to certain
	// category/sub category
	public final static String ENTITY_WISDOM_NUM = "num";

	public final static String ENTITY_WISDOM_ID = "id";

	public final static String ENTITY_WISDOM_TITLE = "title";

	public final static String ENTITY_WISDOM_DESCRIPTION = "description";

	public final static String ENTITY_WISDOM_TAG = "tag";

	public final static String ENTITY_WISDOM_CREATED_USER_ID = "create_user_id";

	public final static String ENTITY_WISDOM_LAST_UPDATED_DATE = "last_updated_date";

	public final static String ENTITY_WISDOM_THUMBNAIL = "thumbnail";

	public final static String ENTITY_WISDOM_ITMES = "items";

	// Test fields
	public final static String ENTITY_ID = "id";

	public final static String ENTITY_TITLE = "title";

	public final static String ENTITY_DESCRIPTION = "description";

	/**
	 * Separator for Wisdom item
	 */
}
