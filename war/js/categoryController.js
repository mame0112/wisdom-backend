wisdomApp.controller('categoryController', 
	['$scope', 
	'$http', 
	'log', 
	'modeService', 
	'Constants', 
	'$routeParams', 
	'categoryAPIService',
	'timeFormatService',
 function($scope, $http, log, modeService, Constants, $routeParams, categoryAPIService, timeFormatService){
 	log.d("categoryController");

 	var ACTIVE = "active";
 	var DISAVLED = "disabled";
 	var ITEM_IN_PAGE = 10;

 	var currentPage = 0;

 	// $scope.pagination = paginationFactory.getNew();
 	// $scope.pagination = paginationFactory.getNew(10);
 	// $scope.pagination.numPages = 3;

 	$scope.categoryId = $routeParams.categoryId;
 	log.d("categoryid: " + $scope.categoryId);

	$scope.subCategoryId = $routeParams.subCategoryId;
 	log.d("subCategoryName: " + $scope.subCategoryId);

 	var param = {
 		"categoryName": $scope.categoryId,
 		"subCategoryName": $scope.subCategoryId,
 	};

 	//Variables for pagination
 	$scope.totalItems = 0;
	$scope.currentPage = 1;


 	//Put timeFormatService to $scope so that we can use this service from HTML side.
 	$scope.timeFormatService = timeFormatService;

 	// Load categories
 	categoryAPIService.category({servlet_category_param : param}, function(response){
 		// log.d("response.params: " + response.params);
 		// log.d("response.params.limit: " + response.params.limit);

 		var params = response.params;

 		if(params !== null && params !== undefined){
	 		$scope.categories = params.wisdoms;
	 		$scope.wisdomNum = params.wisdomNum;
	 		$scope.wisdoms = params.wisdoms;
	 		$scope.categoryName = params.categoryName;
	 		$scope.subCategoryName = params.subCategoryName;

		 	$scope.totalItems = params.wisdomNum;
 		} else {
 			//Error handling
 		}


 	});

	// modeService.changeCurrentMode();


 	 $scope.isActive = function(){
 	 	log.d("isActive");
 	 	return ACTIVE;
 	 };


	$scope.setPage = function (pageNo) {
		$scope.currentPage = pageNo;
	};

	$scope.pageChanged = function() {
		log.d('Page changed to: ' + $scope.currentPage);
	};


 	 // var shouldPrevBeShown = function(){
 	 // 	log.d("shouldPrevBeShown: " + $scope.totalNum);

 	 // 	var pageNum = parseInt($scope.totalNum/ITEM_IN_PAGE);

 	 // 	log.d("pageNum: " + pageNum);

 	 // 	if(pageNum >= 1){
 	 // 		return true;
 	 // 	} else {
 	 // 		return false;
 	 // 	}
 	 // };

}]);
