wisdomApp.controller('UserDataController', ['$scope', '$routeParams', function($scope, $routeParams){
 	console.log("UserDataController");
 	$scope.userId = $routeParams.userId;
 	console.log("userId: " + $scope.userId);
}]);
