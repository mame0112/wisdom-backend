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

	$scope.categories = Constants.Category;

	$scope.category = $scope.categories[2];

 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);
}]);
