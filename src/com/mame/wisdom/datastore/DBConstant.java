package com.mame.wisdom.datastore;

public class DBConstant {

	/**
	 * Datastore kinds
	 */
	public final static String KIND_ALL_USER = "kind_all_user";

	public final static String KIND_ALL_WISDOM = "kind_all_wisdom";

	// User's not to be changed information. (e.g. mail address)
	public final static String KIND_USER_DATA = "kind_user_data";

	// User's to be changed information. (e.g. point)
	public final static String KIND_USER_STATUS = "kind_user_status";

	public final static String KIND_SUB_CATEGORY = "kind_sub_category";

	public final static String KIND_WISDOM = "kind_wisdom";

	public final static String KIND_UPDATE_INFO = "kind_update_info";

	/**
	 * Entity fields for all user data
	 */
	public final static String ENTITY_TOTAL_USER_NUMBER = "total_user_number";

	/**
	 * Entity fields for all wisdom data
	 */
	public final static String ENTITY_TOTAL_WISDOM_NUMBER = "total_wisdom_number";

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

	public final static String ENTITY_USER_MAIL_ADDRESS = "user_mailaddress";

	/**
	 * Entity fields for user status data
	 */
	public final static String ENTITY_STATUS_TOTAL_POINT = "Status_total_point";

	public final static String ENTITY_STATUS_CREATED_WISDOM = "Status_created_wisdom";

	public final static String ENTITY_STATUS_LIKED_WISDOM = "Status_liked_wisdom";

	/**
	 * Entity fields for Category data
	 */
	public final static String ENTITY_CATEGORY_DESCRIPTION = "description";

	public final static String ENTITY_CATEGORY_WISDOM_IDS = "wisdomIds";

	/**
	 * Entity fields for Wisdom data
	 */
	// This is a field for the number of wisdom which belong to certain
	// category/sub category
	public final static String ENTITY_WISDOM_NUM = "num";

	public final static String ENTITY_WISDOM_ID = "id";

	public final static String ENTITY_WISDOM_CATEGORY = "category";

	public final static String ENTITY_WISDOM_SUBCATEGORY = "subcategory";

	public final static String ENTITY_WISDOM_TITLE = "title";

	public final static String ENTITY_WISDOM_DESCRIPTION = "description";

	public final static String ENTITY_WISDOM_TAG = "tag";

	public final static String ENTITY_WISDOM_CREATED_USER_ID = "create_user_id";

	public final static String ENTITY_WISDOM_LAST_UPDATED_DATE = "last_updated_date";

	public final static String ENTITY_WISDOM_THUMBNAIL = "thumbnail";

	public final static String ENTITY_WISDOM_ITMES = "items";

	public final static String ENTITY_WISDOM_VIEWED_COUNT = "count";

	// Test fields
	public final static String ENTITY_ID = "id";

	public final static String ENTITY_TITLE = "title";

	public final static String ENTITY_DESCRIPTION = "description";

	/**
	 * Separator for Wisdom item
	 */
}
