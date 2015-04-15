wisdomApp.controller('searchResultController',
 ['$scope', 'log', '$stateParams',  'searchAPIService',
 function($scope, log, $stateParams, searchAPIService){

	// var originalParams = JSON.parse($scope.params);
	$scope.searchWord = $stateParams.result;

	$scope.wisdoms = null;

	if($scope.searchWord !== null && $scope.searchWord !== undefined){
		// log.d("input value: " + data);
		searchAPIService.search({searchParam : $scope.searchWord}, function(response){
			log.d("response received: " + response.params[0].title);
			if(response !== null && response !== undefined){

				$scope.wisdoms = response.params;
				// $scope.searchResult = response.params;
				// $scope.params = response.params[0].title;
				log.d("params:: " + response.params[0].title);
				// log.d("id: " + response.id);
				// log.d("id: " + response.version);
 			// $state.go('search', { params: JSON.stringify(response)});
			} else {

			}


			// $location.path('/new').search('x=y');
			// $window.location.href = '/tos';
		});
	}


	// $scope.input = $scope.params;

 	$scope.initialize = function()
 	{
 		// log.d("initialize");
 		// var params = $scope.params;
 		// log.d("title: " + params[0].title);
 		// if($scope.params !== null && $scope.params !== undefined){
 		// 	log.d("params: " + params.params[0].updated_date);
	 	// }
 	};

 }]);