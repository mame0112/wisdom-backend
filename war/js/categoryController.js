wisdomApp.controller('categoryController', 
	['$scope', 
	'$http', 
	'log', 
	'modeService', 
	'Constants', 
	'$stateParams', 
	'categoryAPIService',
	'timeFormatService',
	'creativeColorGenerateService',
 function($scope, $http, log, modeService, Constants, $stateParams, categoryAPIService, timeFormatService, creativeColorGenerateService){
 	log.d("categoryController");

 	var ACTIVE = "active";
 	var DISAVLED = "disabled";
 	var ITEM_IN_PAGE = 10;

 	var currentPage = 0;

 	// $scope.pagination = paginationFactory.getNew();
 	// $scope.pagination = paginationFactory.getNew(10);
 	// $scope.pagination.numPages = 3;

 	$scope.categoryId = $stateParams.categoryId;
 	log.d("categoryid: " + $scope.categoryId);

	$scope.subCategoryId = $stateParams.subCategoryId;
 	log.d("subCategoryName: " + $scope.subCategoryId);

 	$scope.creativeColorGenerate = creativeColorGenerateService;

 	var offset = 0;
 	var limit = 10;

 	$scope.start = 0;
 	$scope.end = 0;

 	var param = {
 		"offset" : offset,
 		"limit" : limit,
 		"categoryName": $scope.categoryId,
 		"subCategoryName": $scope.subCategoryId,
 	};

 	//Variables for pagination
 	$scope.totalItems = 0;
	$scope.currentPage = 1;

 	//Put timeFormatService to $scope so that we can use this service from HTML side.
 	$scope.timeFormatService = timeFormatService;

 	$scope.initialize = function(){
 		log.d("categoryController initialize");
		modeService.changeCurrentMode(Constants.STATE.STATE_CATEGORY_PAGE);
		$scope.loadCategoryData(0);
 	};


	// modeService.changeCurrentMode();


 	 $scope.isActive = function(){
 	 	log.d("isActive");
 	 	return ACTIVE;
 	 };


	$scope.setPage = function (pageNo) {
		log.d("setPage: " + pageNo);
		$scope.currentPage = pageNo - 1;
		$scope.loadCategoryData($scope.currentPage);
	};

	$scope.pageChanged = function(page) {
		log.d("new page: " + page);
		$scope.currentPage = page -1;
		// log.d('Page changed to: ' + $scope.currentPage);
		$scope.loadCategoryData($scope.currentPage);
	};

	$scope.loadCategoryData = function(currentPage){
		log.d("currentPage: " + currentPage);

		param.offset = currentPage * param.limit;
		$scope.start = param.offset;

	 	// Load categories
	 	categoryAPIService.category({servlet_params : param}, function(response){
 		// log.d("response.params: " + response.params);
 		// log.d("response.params.limit: " + response.params.limit);

 		var params = response.params;

 		if(params !== null && params !== undefined){
 			log.d("params: " + params);
	 		$scope.categories = params.wisdoms;
	 		$scope.wisdomNum = params.wisdomNum;
	 		$scope.wisdoms = params.wisdoms;
	 		$scope.categoryName = params.categoryName;
	 		$scope.subCategoryName = params.subCategoryName;

		 	$scope.totalItems = params.wisdomNum;

			//If we don't reach to end
			if($scope.wisdomNum >= $scope.start + param.limit){
				$scope.end = $scope.start + param.limit;
			} else {
				//If we reach to end, end equals with total num
				$scope.end = $scope.wisdomNum;
			}

 		} else {
 			//Error handling
 		}


 	});

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
