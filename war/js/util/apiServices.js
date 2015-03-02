wisdomApp.factory('apiService', ['$resource', 'log', function($resource, log) {
	return {
      infoa:function(){
        log.d("infoa");        
        var response = $resource('/controller/signin', 
    		{servlet_resp_id: 1, twitter_name: 'TwitterName'},
    		 {query: {method: 'GET', isArray: false}
    		});
        log.d("response: " + response);
        }
    };
	}
]);
    // return $resource('api/wisdom/:id', {}, {
    //   // Get certain or all category
    //   get_category: {method: 'GET', isArray: false},
    //   // Add as POST
    //   save: {method: 'POST'}
    // });


// wisdomApp.factory('apiService', ['$resource',
//   function($resource) {
//     return $resource('api/wisdom/:id', {}, {
//       // Get certain or all category
//       get_category: {method: 'GET', isArray: false},
//       // Add as POST
//       save: {method: 'POST'}
//     });
//   }
// ]);