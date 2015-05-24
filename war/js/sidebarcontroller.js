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

					var categoryData = {
						"id": 0,
						"name": null,
						"translate": null
					};

					categoryData.translate = translations[category[index].translate];
					categoryData.id = index;
					categoryData.name = category[index].name;


					$scope.Category[index] = categoryData;
					log.d("$scope.Category[i].translate: " + $scope.Category[index].translate);
	        	}, 0);
			});
	 	}
 	}

 	$scope.selectCategory = function(data)
 	{
 		if(data !== null)
 		{
	 		var category = data;
	 		log.d("input category: " + category.name);
	 		subCategoryLoaderService.load(category.name).then(function(d){
	 			log.d("d: " + d.data);
	 			// $scope.subCategory = d.data;
	 			for(var i=0; i<d.data.length; i++){
	 				log.d("translate: " + d.data[i].translate);
					translateSubCategory(d.data[i].translate, i, d.data[i].name);
	 			}

				function translateSubCategory(value, index, name){
					$translate([
						value
						])
					.then(function (translations) {
						$timeout(function() {

							log.d("name: " + name);

							var categoryData = {
								"name": name,
								"translate": translations[value]
							};

							// $scope.subCategory[index] = translations[value];
							$scope.subCategory[index] = categoryData;
							log.d("translations[value]: " + translations[value]);
							// log.d("$scope.subCategory[i]: " + $scope.subCategory[index]);
			        	}, 0);
					});
				}
	 		});

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
