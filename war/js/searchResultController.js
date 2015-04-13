wisdomApp.controller('searchResultController',
 ['$scope', 'log', '$stateParams', 
 function($scope, log, $stateParams){

	$scope.params = JSON.parse($stateParams.params);
	// var originalParams = JSON.parse($scope.params);
	log.d("params: " + $scope.params.version);

 	$scope.initialize = function()
 	{
 		log.d("initialize");
 		// var params = $scope.params;
 		// log.d("title: " + params[0].title);
 		// if($scope.params !== null && $scope.params !== undefined){
 		// 	log.d("params: " + params.params[0].updated_date);
	 	// }
 	};

 }]);