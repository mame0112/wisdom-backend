wisdomApp.controller('wisdomCreateController',
 ['$scope',
   'log',
   'Constants',
   'createWisdomSharedStateService',
   'wisdomAPIService',
   '$http',
   'userDataHolder',
   'timeService',
   'subCategoryLoaderService',
    function($scope, log, Constants, createWisdomSharedStateService, wisdomAPIService, $http, userDataHolder, timeService, subCategoryLoaderService){
 	log.d("wisdomCreateController");

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	$scope.categories = Constants.Category;
	$scope.subCategories = null;

	var userData = null;

	var category = null;
	var subCategory = null;
	var title = null;
	var tag = null;
	var thumbnail = null;
	var description = null;
	var messages = null;
	var createUserId = -1;
	var updateDate = -1;

	var result = {
		"id": -1,
		"category": category,
		"subCategory": subCategory,
		"title": title,
		"tag": tag,
		"thumbnail": thumbnail,
		"description": description,
		"messages" : messages,
		"create_user_id": createUserId,
		"updated_date": updateDate,
	};

	$scope.initialize = function(){
		// userData =  userDataHolder.getUserData();
		// log.d("userId: " + userData.params.userId);
		// result.createUserId = userData.params.userId;
	};


	$scope.addNewWisdom = function(){
		log.d("addNewWisdom");

		userData =  userDataHolder.getUserData();
		log.d("userId: " + userData.params.userId);
		result.create_user_id = userData.params.userId;
		result.updated_date = timeService.getCurrentTime();

		result.messages = createWisdomSharedStateService.getSharedMessages();
		log.d("result: " + "category: " + result.category + "subCategory: " + result.subCategory +  " tag: " + result.tag + " thumbnail: " + result.thumbnail + " title: " + result.title + " description: " + result.description + "messages: " + result.messages + " createUserId: " + result.create_user_id + " updated time: " + result.updated_date);

		wisdomAPIService.newwisdom({servlet_new_wisdom_param : result});

	};

	$scope.$watch('category', function(newValue, oldValue) {
		result.category = newValue;
	});

	$scope.$watch('subCategory', function(newValue, oldValue) {
		result.subCategory = newValue;
	});

	$scope.$watch('title', function(newValue, oldValue) {
		result.title = newValue;
	});

	$scope.$watch('description', function(newValue, oldValue) {
		result.description = newValue;
	});

	$scope.$watch('tag', function(newValue, oldValue) {
		result.tag = newValue;
	});

	$scope.$watch('thumbnail', function(newValue, oldValue) {
		result.thumbnail = newValue;
	});

	$scope.getSubcategoryItems = function(category)
	{
		log.d("getSubcategoryItems: " + category);
		if(category !== null) {
	 		subCategoryLoaderService.load(category).then(function(d){
	 			log.d("d: " + d.data);
	 			$scope.subCategories = d.data;
	 		});

			// var jsonFile = category.toLowerCase();

			// $http({
			// 	method: 'GET', 
			// 	url: '/data/' + jsonFile + '.json'
			// }).
	  // 		success(function(data, status, headers, config) {
	  // 			log.d("success: " + data);
	  // 			$scope.subCategoryTitles = data;
	  // 		}).
			// error(function(data, status, headers, config) {
			// 	console.log('error');
			// });
		}
	};

	// var findSubCategoryByKey = function(category)
	// {
	// 	log.d("findSubCategoryByKey: " + category);
	// 	if(category === 'SPORTS')
	// 	{
	// 	log.d("AAAAAAAAAAAAAAAA");
	// 		$scope.subCategories = Constants.SUB_SPORTS;
	// 	}


	// };

}]);
