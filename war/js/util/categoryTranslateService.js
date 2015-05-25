wisdomApp.factory('categoryTranslateService', ['$rootScope', 'log', 'Constants', '$translate', '$timeout', '$q',
	function($rootScope, log, Constants, $translate, $timeout, $q) {
	return {
		getTranslatedCategories: function()
		{

			log.d("getTranslatedCategories");
			var categories = [];
			var promises = [];

	 		for(var i=0; i<Constants.Category.length; i++) {
	 			// categories.push(getPromiseForSingleTranslation(Constants.Category[i]));
	 			var promise = getPromiseForSingleTranslation(Constants.Category[i]);
	 			promises.push(promise);
	 		}

	 		return $q.all(promises);

			function getPromiseForSingleTranslation (category)
			{
				var deferred = 	$q.defer();

				$translate([
					category.translate
					])
				.then(function (translations) {

					var categoryData = {
						id: 0,
						name: null,
						translate: null
					};

					var notifyObj;
					deferred.notify(notifyObj);
					if(translations !== undefined && category.translate !== undefined){
						// var resolveObj;
						categoryData.name= category.name;
						categoryData.id = category.id;
						categoryData.translate = translations[category.translate];

						categories.push(categoryData);

						deferred.resolve(categoryData);
					} else {
						var rejectObj ="Couldnot tranlated";
						deferred.reject(rejectObj);
					}

				});

				return deferred.promise;
			}
		},

		getTranslatedSubCategories: function(subCategories)
		{
			log.d("getTranslatedSubCategories");
			var output = [];
			var promises = [];

	 		for(var i=0; i<subCategories.length; i++) {
	 			// categories.push(getPromiseForSingleTranslation(Constants.Category[i]));
	 			var promise = getPromiseForSingleTranslation(subCategories[i]);
	 			promises.push(promise);
	 		}

	 		return $q.all(promises);

			function getPromiseForSingleTranslation (sc)
			{
				var deferred = 	$q.defer();

				$translate([
					sc.translate
					])
				.then(function (translations) {

					var subCategoryData = {
						id: 0,
						name: null,
						translate: null
					};

					var notifyObj;
					deferred.notify(notifyObj);
					if(translations !== undefined && sc.translate !== undefined){
						// var resolveObj;
						subCategoryData.name= sc.name;
						subCategoryData.id = sc.id;
						subCategoryData.translate = translations[sc.translate];

						output.push(subCategoryData);

						deferred.resolve(subCategoryData);
					} else {
						var rejectObj ="Couldnot tranlated";
						deferred.reject(rejectObj);
					}

				});

				return deferred.promise;
			}
		}
	};

}]);