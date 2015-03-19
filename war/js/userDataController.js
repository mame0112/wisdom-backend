wisdomApp.controller('UserDataController',
 ['$scope', 
 '$routeParams', 
 'userInfoAPIService',
 'log',
 'Constants',
 function($scope, $routeParams, userInfoAPIService, log, Constants){

 	console.log("UserDataController");
 	$scope.userId = $routeParams.userId;
 	console.log("userId: " + $scope.userId);

 	var offset = 0;
 	var limit = 5;

 	var param = {
 		"userId" : $scope.userId,
 		"offset" : offset,
 		"limit" : limit
 	};


 	$scope.initialize = function()
 	{
 		log.d("initialize");
 		$scope.loadUseData($scope.userId);
 	};

 	$scope.loadUseData = function(userId)
 	{
 		if(userId !== null && userId !== Constants.NO_USER)
 		{
 			userInfoAPIService.status({servlet_params : param}, function(response){
 				log.d("response received");
 			});
 		}
 	};


}]);
