wisdomApp.factory('apiService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/infobar',
         {servlet_resp_id: 1, twitter_name: 'TwitterName'},
         { 
            popular: {method: 'GET', isArray: false
        }
     });
    }
])

.factory('userAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/user',
         {},
         { 
            //Sign in
            signin: {method: 'GET', isArray: false},

            //Sign up (create account)
            signup: {method: 'POST', isArray: false}
        });
    }
])

.factory('userInfoAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/userInfo',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get user info
            status: {method: 'GET', isArray: false},
        });
    }
])

.factory('userRankingAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/ranking',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get user info
            status: {method: 'GET', isArray: false},
        });
    }
])

.factory('notificationAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/notification',
         {},
         { 
            //Latest status
            latest: {method: 'GET', isArray: false},

            //Change read state after the user read latest notifications
            state: {method: 'POST', isArray: false}
        });
    }
])

.factory('wisdomAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/wisdom:param',
         {servlet_resp_id: 1, param: '@servlet_new_wisdom_param'},
         { 
            //Get one wisdom (by key, under certain category/subcategory)
            wisdom: {method: 'GET', isArray: false},

            // //Create new wisdom
            // newwisdom: {method: 'GET', isArray: false},

            // //Update wisdom
            // updatewisdom: {method: 'PUT', isArray: false},
        });
    }
])

.factory('newWisdomAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/newwisdom:param',
         {servlet_resp_id: 1, param: '@servlet_new_wisdom_param'},
         { 
            //Create new wisdom
            newwisdom: {method: 'GET', isArray: false},
        });
    }
])

.factory('modifyWisdomAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/modifywisdom:param',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Create new wisdom
            modifywisdom: {method: 'GET', isArray: false},
        });
    }
])


.factory('categoryAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/category:param',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get one wisdom (by key, under certain category/subcategory)
            category: {method: 'GET', isArray: false},
        });
    }
])

.factory('highlightAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/highlight',
         {servlet_resp_id: 1},
         { 
            //Get today's highlight wisdoms
            highlight: {method: 'GET', isArray: false},
        });
    }
])

.factory('latestAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/latest',
         {servlet_resp_id: 1},
         { 
            //Get latest wisdoms
            latest: {method: 'GET', isArray: false},
        });
    }
])

.factory('searchAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/search:key',
         {servlet_resp_id: 1, key: '@searchParam'},
         { 
            //Search widoms by given keyword
            search: {method: 'GET', isArray: false},
        });
    }
])

.factory('twitterAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/twitter',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            account: {method: 'GET', isArray: false},
        });
    }
])

.factory('userNameAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/useraccount',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            useraccount: {method: 'GET', isArray: false},
        });
    }
])

.factory('userLoginAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/userlogin',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            login: {method: 'GET', isArray: false},
        });
    }
])

.factory('contactAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/contact',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            mail: {method: 'GET', isArray: false},
        });
    }
])

.factory('wisdomMessageLikeAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/messagelike',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            like: {method: 'GET', isArray: false},
        });
    }
])

.factory('facebookSignupAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/facebookSignup',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Search widoms by given keyword
            facebookSignup: {method: 'GET', isArray: false},
        });
    }
])

.factory('facebookSigninAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/facebookSignin',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Search widoms by given keyword
            facebookSignin: {method: 'GET', isArray: false},
        });
    }
])
;
