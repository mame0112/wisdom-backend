// wisdomApp.factory('apiService', ['$resource', 'log',
//  function($resource, log) {
//     return ($resource('/controller/infobar',
//      {},
//      {
//         popular: {method: 'GET', isArray: false}
//      })
//     );
//   }
// ]);

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

            //Create new wisdom
            newwisdom: {method: 'GET', isArray: false},

            //Update wisdom
            updatewisdom: {method: 'PUT', isArray: false},
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
]);

