wisdomApp.factory('categoryTranslateService', ['$rootScope', 'log', 'Constants', '$translate', '$timeout', '$q',
	function($rootScope, log, Constants, $translate, $timeout, $q) {
	return {
		getTranslatedCategories: function()
		{

			log.d("getTranslatedCategories");

			// var categoryDatas = [];

			// return getPromiseForSingleTranslation();

			var categories = [];
			var promises = [];

	 		for(var i=0; i<Constants.Category.length; i++) {
	 			// categories.push(getPromiseForSingleTranslation(Constants.Category[i]));
	 			var promise = getPromiseForSingleTranslation(Constants.Category[i]);
	 			promises.push(promise);
	 		}

	 		//Return promise
	 		return $q.all(promises);
	 		// return promiseAll;
	 		// promiseAll.then(function(){
	 		// 	log.d("category size: " + categories.length);
	 		// 	return categories;
	 		// });

	 		// return $q.all(promises);
	 		// promiseAll.then(
	 		// 	function(){
	 		// 		log.d("promise all then: " + categories.length);
			 // 		return categories;
	 		// 	}
	 		// );



			// var promises = [];

	 	// 	for(var i=0; i<Constants.Category.length; i++) {
	 	// 		log.d("Constants.Category[i]" + Constants.Category[i].name);
	 	// 		promises.push(getPromiseForSingleTranslation(Constants.Category[i]));
	 	// 	}

	 	// 	return $q.all(promises[0], promises[1]);


	 		// 	var categoryData = translate(Constants.Category[i], i);
	 		// 	// log.d("categoryData: " + categoryData.id + " / " + categoryData.name + " / " + categoryData.translate);
	 		// 	categoryDatas[i] = categoryData;
	 		// }

	 	// 	var category = Constants.Category[0];

			// var deferred = 	$q.defer();

			// $translate([
			// 	category.translate
			// 	])
			// .then(function (translations) {

			// 	var notifyObj;
			// 	deferred.notify(notifyObj);
			// 	if(translations !== undefined && category.translate !== undefined){
			// 		// var resolveObj;
			// 		categoryData.name= category.name;
			// 		categoryData.id = category.id;
			// 		categoryData.translate = translations[category.translate];

			// 		categoryDatas.push(categoryData);

			// 		deferred.resolve(categoryDatas);
			// 	} else {
			// 		var rejectObj;
			// 		deferred.reject(rejectObj);
			// 	}

			// });

			// return deferred.promise;



			// translateCategory2(
			// 	$translate([
			// 		category.translate
			// 		])
			// 	.then(function (translations) {
			// 		// $timeout(function() {

			// 		categoryData.translate = translations[category.translate];
			// 		categoryData.id = index;
			// 		categoryData.name = category.name;

			// 		log.d("categoryData.translate:: " + categoryData.translate + " categoryData.id: " + categoryData.id + " categoryData.name: " + categoryData.name);

			// 		return categoryData;

			// 			// $scope.Category[index] = categoryData;
			// 			// log.d("$scope.Category[i].translate: " + $scope.Category[index].translate);
		 //        	// }, 0);
			// 	})
			// );

			// return deferred.promise();

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

						// categoryDatas.push(categoryData);
						log.d("categoryData.name: " + categoryData.name + "categoryData.translate: " + categoryData.translate);
						categories.push(categoryData);

						deferred.resolve(categoryData);
					} else {
						var rejectObj ="Couldnot tranlated";
						deferred.reject(rejectObj);
					}

				});

				return deferred.promise;
			}


			// return translateCategories();
		},

		setTitle: function(newTitle)
		{
			log.d("setTitle: " + newTitle);
			$rootScope.pageTitle = newTitle;
		}
	};



 	function translateCategories()
 	{

 		log.d("translateCategories");

 		var categoryDatas = {};
 		for(var i=0; i<Constants.Category.length; i++) {
 			var categoryData = translate(Constants.Category[i], i);
 			// log.d("categoryData: " + categoryData.id + " / " + categoryData.name + " / " + categoryData.translate);
 			categoryDatas[i] = categoryData;
 		}

 		return categoryDatas;

	 	function translate (category, index){

			var categoryData = {
				id: 0,
				name: null,
				translate: null
			};

			$translate([
				category.translate
				])
			.then(function (translations) {
				// $timeout(function() {

					categoryData.translate = translations[category.translate];
					categoryData.id = index;
					categoryData.name = category.name;

					log.d("categoryData.translate:: " + categoryData.translate + " categoryData.id: " + categoryData.id + " categoryData.name: " + categoryData.name);

					return categoryData;

					// $scope.Category[index] = categoryData;
					// log.d("$scope.Category[i].translate: " + $scope.Category[index].translate);
	        	// }, 0);
			});
	 	}
 	}

}]);