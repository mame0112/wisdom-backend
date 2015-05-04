wisdomApp.controller('SidebarController',
 ['$scope', 'Constants', 'log', 'subCategoryLoaderService',
  function($scope, Constants, log, subCategoryLoaderService){

 	$scope.Category = Constants.Category;
 	$scope.subCategory = {};

 	$scope.initialize = function() {
		log.d("initialize");
		setupItems();

		// subCategoryLoaderService.load();

 	};

 	$scope.isVisible = function() {
		log.d("isVisible");
 	};

 	$scope.selectCategory = function(data)
 	{
 		if(data !== null)
 		{
	 		var category = data.name;
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
