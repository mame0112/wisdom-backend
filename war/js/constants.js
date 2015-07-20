wisdomApp.constant(
	"Constants", {
		planetName: 'Greasy Giant',

		testMessage: 'test message from Constants',

		API_V1 : '/api/v1',

		Category: [
			{id: 0, name: "Sports", translate:"category.sports"},
			{id: 1, name: "Music", translate:"category.music"},
			{id: 2, name: "Cooking", translate:"category.cooking"},
			{id: 3, name: "Language", translate:"category.language"},
			{id: 4, name: "Business", translate:"category.business"},
			{id: 5, name: "Fashion", translate:"category.fashion"},
			{id: 6, name: "Art", translate:"category.art"},
			{id: 7, name: "Wa", translate:"category.wa"}
		],

		MODE_CHNAGE: 'mode_changed',

		STATE: {
			STATE_HOME_NO_LOGIN: "state_home_no_login",
			STATE_HOME_LOGIN: "state_home_login",
			STATE_SIGNIN_PAGE: "state_signin_page",
			STATE_SIGNUP_PAGE: "state_signup_page",
			STATE_USER_PAGE: "state_user_page",
			STATE_USER_DETAIL_PAGE: "state_user_detail_page",
			STATE_CATEGORY_PAGE: "state_category_page",
			STATE_WISDOM_PAGE: "state_widsom_page",
			STATE_WISDOM_CREATE_PAGE: "state_widsom_create_page"
		},

		PAGING: "paging",

		NO_USER: -1,

		TOASTER_SHORT: 3000,

		TOASTER_LONG: 5000,

		WISDOM_TITLE_MIN_COUNT : 2,
		WISDOM_TITLE_MAX_COUNT : 8,

		WISDOM_TAG_MIN_COUNT : 2,
		WISDOM_TAG_MAX_COUNT : 8,

		WISDOM_TITLE_COUNT: 50,

		WISDOM_DESCRIPTION_COUNT: 500,

		//Constants for UserIdValidator
 		VALIDATE_RESULT : {
 			"VALID_ID": "valid user id",
 			"UNDEFINED": "user id is null or undefined",
 			"ILLEGAL_ID": "illegal user id",
 			"ID_NOT_OWNED_BY_USER": "id is not owned by user"
 		},


	}
);
