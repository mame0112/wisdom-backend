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
    return $resource('/controller/wisdom:key',
         {},
         { 
            //Get one wisdom (by key, under certain category/subcategory)
            wisdom: {method: 'GET', isArray: false},

            //Create new wisdom
            newwisdom: {method: 'POST', isArray: false},

            //Update wisdom
            updatewisdom: {method: 'PUT', isArray: false},
        });
    }
])

.factory('highlightAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/highlight',
         {},
         { 
            //Get today's highlight wisdoms
            highlight: {method: 'GET', isArray: false},
        });
    }
])

.factory('latestAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/latest',
         {},
         { 
            //Get latest wisdoms
            highlight: {method: 'GET', isArray: false},
        });
    }
])

.factory('searchAPIService', ['$resource', 'log',
 function($resource, log) {
    return $resource('/controller/search:key',
         {},
         { 
            //Search widoms by given keyword
            search: {method: 'GET', isArray: false},
        });
    }
]);

// .factory('twitterAPIService', ['$resource', 'log',
//  function($resource, log) {
//     return $resource('/twitter',
//          {},
//          { 
//             //Search widoms by given keyword
//             account: {method: 'POST', isArray: false},
//         });
//     }
// ]);

    // function infobar2 ($resource){
    //     $resource('/controller/infobar2',
    //      {},
    //      { 
    //         popular: {method: 'GET', isArray: false
    //     }
    //  });
    // }

    // return{
    //     infobar:function(){
    //         log.d("infobar...");
    //         return infobar($resource);
    //     },
    //     infobar2:function(){
    //         log.d("infobar2...");
    //         return infobar2($resource);
    //     }
    // };




	// return {
 //      infoa:function(){
 //        log.d("infoa");        
 //        var response = $resource('/controller', 
 //    		{servlet_resp_id: 1, twitter_name: 'TwitterName'}, {
 //                signin: {method: 'GET', isArray: false}
 //    		});
 //        log.d("response: " + response);
 //        }
 //    };
