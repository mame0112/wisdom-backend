wisdomApp.constant(
	"Constants", {
		planetName: 'Greasy Giant',

		testMessage: 'test message from Constants',

		Category: [
			{id: 0, name: "Sports", translate:"category.sports"},
			{id: 1, name: "Music", translate:"category.music"},
			{id: 2, name: "Cooking", translate:"category.cooking"},
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

		WISDOM_TITLE_COUNT: 50,

	}
);
