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
		findSubCategoryByKey(newValue);
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

	var findSubCategoryByKey = function(category)
	{
		if(category === 'SPORTS')
		{
			$scope.subCategories = Constants.SUB_SPORTS;
		}


	};

}]);
