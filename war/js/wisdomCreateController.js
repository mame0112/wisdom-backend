wisdomApp.controller('wisdomCreateController',
 ['$scope',
  '$routeParams',
   'log',
    function($scope, $routeParams, log){
 	log.d("wisdomCreateController");

	$scope.status = {
		isopen1: false,
		isopen1: false
	};
 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);
}]);
