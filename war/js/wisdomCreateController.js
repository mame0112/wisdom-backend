wisdomApp.controller('wisdomCreateController',
 ['$scope',
  '$routeParams',
   'log',
   'Constants',
    function($scope, $routeParams, log, Constants){
 	log.d("wisdomCreateController");

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	$scope.category = Constants.Category;

 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);
}]);
