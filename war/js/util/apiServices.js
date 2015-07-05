wisdomApp.factory('apiService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/infobar',
         {servlet_resp_id: 1, twitter_name: 'TwitterName'},
         { 
            popular: {method: 'GET', isArray: false
        }
     });
    }
])

.factory('userAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/user',
         {},
         { 
            //Sign in
            signin: {method: 'GET', isArray: false},

            //Sign up (create account)
            signup: {method: 'POST', isArray: false}
        });
    }
])

.factory('userInfoAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/userInfo',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get user info
            status: {method: 'GET', isArray: false},
        });
    }
])

.factory('userRankingAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    // return $resource('/controller/ranking',
    return $resource(Constants.API_V1 + '/ranking',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get user info
            status: {method: 'GET', isArray: false},
        });
    }
])

.factory('notificationAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/notification',
         {},
         { 
            //Latest status
            latest: {method: 'GET', isArray: false},

            //Change read state after the user read latest notifications
            state: {method: 'POST', isArray: false}
        });
    }
])

.factory('wisdomAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/wisdom:param',
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

.factory('newWisdomAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/newwisdom:param',
         {servlet_resp_id: 1, param: '@servlet_new_wisdom_param'},
         { 
            //Create new wisdom
            newwisdom: {method: 'GET', isArray: false},
        });
    }
])

.factory('modifyWisdomAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/modifywisdom:param',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Create new wisdom
            modifywisdom: {method: 'GET', isArray: false},
        });
    }
])


.factory('categoryAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/category:param',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Get one wisdom (by key, under certain category/subcategory)
            category: {method: 'GET', isArray: false},
        });
    }
])

.factory('highlightAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/highlight',
         {servlet_resp_id: 1},
         { 
            //Get today's highlight wisdoms
            highlight: {method: 'GET', isArray: false},
        });
    }
])

.factory('latestAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/latest',
         {servlet_resp_id: 1},
         { 
            //Get latest wisdoms
            latest: {method: 'GET', isArray: false},
        });
    }
])

.factory('searchAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/search:key',
         {servlet_resp_id: 1, key: '@searchParam'},
         { 
            //Search widoms by given keyword
            search: {method: 'GET', isArray: false},
        });
    }
])

.factory('twitterAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/twitter',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            account: {method: 'GET', isArray: false},
        });
    }
])

.factory('userNameAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/useraccount',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            useraccount: {method: 'GET', isArray: false},
        });
    }
])

.factory('userLoginAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/userlogin',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            login: {method: 'GET', isArray: false},
        });
    }
])

.factory('contactAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/contact',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            mail: {method: 'GET', isArray: false},
        });
    }
])

.factory('wisdomMessageLikeAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/messagelike',
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            like: {method: 'GET', isArray: false},
        });
    }
])

.factory('facebookSignupAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/facebookSignup',
         // {servlet_resp_id: 1, param: '@servlet_params'},
         {servlet_resp_id: 1},
         { 
            //Search widoms by given keyword
            facebookSignup: {method: 'GET', isArray: false},
        });
    }
])

.factory('facebookSigninAPIService', ['$resource', 'log', 'Constants',
 function($resource, log, Constants) {
    return $resource(Constants.API_V1 + '/facebookSignin',
         {servlet_resp_id: 1, param: '@servlet_params'},
         { 
            //Search widoms by given keyword
            facebookSignin: {method: 'GET', isArray: false},
        });
    }
]);
