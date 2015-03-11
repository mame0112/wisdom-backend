wisdomApp.controller('wisdomCreateController',
 ['$scope',
   'log',
   'Constants',
    function($scope, log, Constants){
 	log.d("wisdomCreateController");

 	$scope.rootObject = null;

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	$scope.categories = Constants.Category;
	$scope.subCategories = null;

	$scope.category = $scope.categories[2];


	var category = null;
	var title = null;
	var tag = null;
	var thumbnail = null;
	var description = null;

	var result = {
		"category": category,
		"title": title,
		"tag": tag,
		"thumbnail": thumbnail,
		"description": description
	};


	$scope.addNewWisdom = function(){
		log.d("addNewWisdom");
		log.d("result: " + "category: " + result.category + " tag: " + result.tag + " thumbnail: " + result.thumbnail + " title: " + result.title + " description: " + result.description);

	};

	$scope.$watch('category', function(newValue, oldValue) {
		result.category = newValue;
		// findSubCategoryByKey(newValue);
	});

	$scope.$watch('subCategory', function(newValue, oldValue) {
		// result.category = newValue;
		// findSubCategoryByKey(newValue);
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
		switch (category){
			case 'SPORTS':
				log.d("Sports selected");
				$scope.subCategories = Constants.SUB_SPORTS;
			break;
			case 'MUSIC':
				log.d("Music selected");
				$scope.subCategories = Constants.SUB_MUSIC;
			break;
			case 'COOKING':
				log.d("Cooking selected");
				$scope.subCategories = Constants.SUB_COOKING;
			break;
			default:			
				log.d("Unexpected category selected");
			break;

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
