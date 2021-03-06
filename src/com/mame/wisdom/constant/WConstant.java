package com.mame.wisdom.constant;

public class WConstant {

	public final static boolean IS_DEBUG = true;

	/*
	 * This should be true while developing local host.
	 */
	public final static boolean IS_LOCAL = true;

	public final static long NO_USER = -1;

	public final static long NO_WISDOM = -1;

	public final static int TAG_WISDOM_TITLE = 1;

	public final static int TAG_WISDOM_MESSAGE = 2;

	/**
	 * The number of wisdoms to show highlight and pickup
	 */
	public final static int HIGHLIGHT_WISDOM_NUM = 10;

	// Arguments to be used on Servlet
	/**
	 * Generic field
	 */
	public final static String SERVLET_RESP_ID = "servlet_resp_id";

	// THis would be used if cliend side wants to send value to backend
	public final static String SERVLET_PARAMS = "servlet_params";

	/**
	 * Public fields
	 */
	public final static String SERVLET_WISDOM_REQUEST_NUM = "servlet_wisdom_req_num";

	/**
	 * User data field
	 */
	public final static String SERVLET_TWITTER_NAME = "servlet_twitter_name";

	public final static String SERVLET_TWITTER_TOKEN = "servlet_twitter_token";

	public final static String SERVLET_TWITTER_TOKEN_SECRET = "servlet_twitter_token_secret";

	public final static String SERVLET_FACEBOOK_NAME = "servlet_facebook_name";

	public final static String SERVLET_USER_NAME = "servlet_user_name";

	public final static String SERVLET_PASSWORD_NAME = "servlet_password";

	public final static String SERVLET_THUMBNAIL_URL = "servlet_thumbnail_url";
	
	public final static String SERVLET_FACEBOOK_ACCESS_TOKEN = "servlet_facebook_access_token";

	/**
	 * Wisdom data field
	 */
	public final static String SERVLET_WISDOM_CATEGORY = "servlet_wisdom_category";

	public final static String SERVLET_WISDOM_SUB_CATEGORY = "servlet_wisdom_subcategory";

	public final static String SERVLET_WISDOM_ID = "servlet_wisdom_id";

	// This would be used if the user tries to create new wisdom content. This
	// includes more than two title or description content.
	public final static String SERVLET_WISDOM_PARAM = "servlet_new_wisdom_param";

	public final static String SERVLET_WISDOM_SEARCH_PARAM = "searchParam";

	public final static String SERVLET_CATEGORY_PARAM = "servlet_category_param";

	/**
	 * The number of items in one page for search
	 */
	public final static int SEARCH_LIMIT_NUM = 20;

	/**
	 * The number of wisdoms that shall be shown as hightlight or pickup wisdom
	 * on public timeline
	 */
	public final static int PUBLIC_WISDOM_NUM = 10;

	public final static String SEARCH_INDEX = "wisdom_search_db";

}
