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
    	}
    };
    return loader;
  }
]);

