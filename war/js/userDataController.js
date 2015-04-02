wisdomApp.controller('UserDataController',
 ['$scope', 
 '$stateParams', 
 'userInfoAPIService',
 'log',
 'Constants',
 'timeFormatService',
 'creativeColorGenerateService',
 function($scope, $stateParams, userInfoAPIService, log, Constants, timeFormatService, creativeColorGenerateService){

 	console.log("UserDataController");

 	//TODO Need to fix in the future.
 	// $scope.userId = $stateParams.userId;
 	$scope.userId = 1;
 	console.log("userId: " + $scope.userId);

 	//Point info that shall be shown on UI
 	$scope.point = 0;

 	//Message info that shall be shown on UI
 	$scope.messages = null;

 	//Set timeFormatService to $scope so that we can use it from UI part.
 	$scope.timeFormatService = timeFormatService;

 	$scope.userColor = null;

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
 		$scope.userColor = creativeColorGenerateService.generateColor($scope.userId);
 	};

 	$scope.loadUseData = function(userId)
 	{
 		if(userId !== null && userId !== Constants.NO_USER)
 		{
 			userInfoAPIService.status({servlet_params : param}, function(response){
 				log.d("response received");
 				$scope.point = response.params.point;
 				$scope.messages = response.params.messages;
 			});
 		}
 	};


}]);
