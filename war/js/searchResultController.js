wisdomApp.controller('searchResultController',
 ['$scope', 'log', '$stateParams',  'searchAPIService',
 function($scope, log, $stateParams, searchAPIService){

	// var originalParams = JSON.parse($scope.params);
	$scope.searchWord = $stateParams.result;

	$scope.wisdoms = null;

	$scope.wisdomNum = -1;

	if($scope.searchWord !== null && $scope.searchWord !== undefined){
		// log.d("input value: " + data);
		searchAPIService.search({searchParam : $scope.searchWord}, function(response){
			if(response.params.length !== 0){
				log.d("response received: " + response.params[0].title);
				if(response !== null && response !== undefined){

					$scope.wisdoms = response.params;
					// $scope.searchResult = response.params;
					// $scope.params = response.params[0].title;
					// log.d("params:: " + response.params[0].title);
					$scope.wisdomNum = response.params.length;
					log.d("length:: " + $scope.wisdomNum);
	 			// $state.go('search', { params: JSON.stringify(response)});
				} else {
					$scope.wisdomNum = 0;
				}
			} else {
				log.d("no result found");
				//No result fount.
				$scope.wisdomNum = 0;
			}
		});
	}


	// $scope.input = $scope.params;

 	$scope.initialize = function()
 	{

 	};

 }]);