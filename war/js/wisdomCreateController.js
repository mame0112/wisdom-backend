wisdomApp.controller('wisdomCreateController',
 ['$scope',
   'log',
   'Constants',
    function($scope, log, Constants){
 	log.d("wisdomCreateController");

 	$scope.rootObject = null;

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	var title = null;

	$scope.categories = Constants.Category;

	$scope.category = $scope.categories[2];

	$scope.addNewWisdom = function(data){
		log.d("addNewWisdom");

		// var output = {
		// 	"title": this.title
		// };

		log.d("result: " + data);
	};

 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);
}]);
