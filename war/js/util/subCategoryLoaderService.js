// wisdomApp.factory('subCategoryLoaderService', ['$resource', 'log',
//  function($resource, log) {
//     return $resource('/data/' + category.toLowerCase() + '.json',
//          {},
//          { 
// 			load: {method: 'GET', isArray: true},
//         });
//     }
// ]);

wisdomApp.factory('subCategoryLoaderService', ['log', '$http',
 function(log, $http) {
    var loader = {
    	load : function(category)
    	{
    		var jsonFile = category.toLowerCase();
    		log.d("jsonfile: " + jsonFile);

			var promise = $http.get('/data/' + jsonFile + '.json')
			.then(function(result) {
				log.d("loaded: " + result);
				return result;
			});
			return promise;
	  // 		.success(function(data, status, headers, config) {
	  // 			log.d("success: " + data);
	  // 			return data;
	  // 			// $scope.subCategoryTitles = data;
	  // 		})
			// .error(function(data, status, headers, config) {
			// 	console.log('error');
			// });
    	}
    };
    return loader;
}

   //  	resource('/data/' + category.toLowerCase() + '.json',
   //       {},
   //       { 
			// load: {method: 'GET', isArray: true},
   //      });
   //  }
]);


// .factory('subCategoryLoaderService',
//  ['log', '$resource', function(log, $resource){
//     return {
//         load: function(category){
// 			if(category !== null) {
// 				var jsonFile = category.toLowerCase();

// 				return $resource('/data/' + jsonFile + '.json', {}, {
					
// 				});
// 			}
// 		}
// 	};
// }])


			// 	$http({
			// 		method: 'GET', 
			// 		url: '/data/' + jsonFile + '.json'
			// 	}).
		 //  		success(function(data, status, headers, config) {
			// 		log.d('success: ' + data);
		 //  			return data;
		 //  		}).
			// 	error(function(data, status, headers, config) {
			// 		log.d('failed to load subcategory');
			// 		return null;
			// 	});
			// }
    // };


	// return $resource('/data/' + jsonFile + '.json', {}, {
	// 	load: {method: 'GET'},
	// });


    // return {
        // public API
   //      load: function(category){
			// if(category !== null) {
			// 	var jsonFile = category.toLowerCase();

			// 	$http({
			// 		method: 'GET', 
			// 		url: '/data/' + jsonFile + '.json'
			// 	}).
		 //  		success(function(data, status, headers, config) {
			// 		log.d('success: ' + data);
		 //  			return data;
		 //  		}).
			// 	error(function(data, status, headers, config) {
			// 		log.d('failed to load subcategory');
			// 		return null;
			// 	});
			// }
			// return null;
   //      }
    // };
