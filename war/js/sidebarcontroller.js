wisdomApp.controller('SidebarController',
 ['$scope', 'Constants', 'log', 'subCategoryLoaderService', '$translate', '$timeout',
  function($scope, Constants, log, subCategoryLoaderService, $translate, $timeout){


 	$scope.Category = {};
 	// $scope.Category = Constants.Category;
 	$scope.subCategory = {};

 	$scope.initialize = function() {
		log.d("initialize");
		// setupItems();

		translateCategories(Constants.Category);

		// subCategoryLoaderService.load();

 	};

 	$scope.isVisible = function() {
		log.d("isVisible");
 	};

 	function translateCategories(category)
 	{

 		for(var i=0; i<category.length; i++) {
 			translate(i);
 		}

	 	function translate (index){
			$translate([
				category[index].translate
				])
			.then(function (translations) {
				$timeout(function() {
					$scope.Category[index] = translations[category[index].translate];
					log.d("$scope.Category[i]: " + $scope.Category[index]);
	        	}, 0);
			});
	 	}
 	}

 	$scope.selectCategory = function(data)
 	{
 		if(data !== null)
 		{
	 		var category = data;
	 		log.d("input category: " + category);
	 		subCategoryLoaderService.load(category).then(function(d){
	 			log.d("d: " + d.data);
	 			$scope.subCategory = d.data;
	 		});

	 		// $scope.subCategory = subCategoryLoaderService.load(category);
	 		// log.d("$scope.subCategory: " + $scope.subCategory);

	 		// log.d("length: " + $scope.subCategory.length);

	 		// log.d("length: " + $scope.subCategory.length);

	 		// for(i=0; i<$scope.subCategory.length; i++)
	 		// {
	 		// 	var item = $scope.subCategory[i];
	 		// 	log.d("item:" + item);
	 		// }
	 		// var loaderservice = new subCategoryLoaderService();
	 		// $scope.subCategory = subCategoryLoaderService.load(category);
	 		// log.d("subCategory: " + $scope.subCategory);
 		}

 	};

 	var setupItems = function(){
 		log.d("setupItems");
 	};

  $scope.addItem = function() {
    var newItemNo = $scope.items.length + 1;
    $scope.items.push('Item ' + newItemNo);
  };

  $scope.status = {
    isFirstOpen: true,
    isFirstDisabled: false
  };


}]);
