wisdomApp.controller('UserDataController',
 ['$scope', 
 '$stateParams', 
 'userInfoAPIService',
 'log',
 'Constants',
 'timeFormatService',
 'creativeColorGenerateService',
 'userDataHolder',
 function($scope, $stateParams, userInfoAPIService, log, Constants, timeFormatService, creativeColorGenerateService, userDataHolder){

 	console.log("UserDataController");

 	//TODO Need to fix in the future.
 	$scope.userId = $stateParams.userId;
 	// $scope.userId = 1;
 	console.log("userId: " + $scope.userId);

 	//Point info that shall be shown on UI
 	$scope.point = 0;

 	//Created wisdom that shall be shown on UI
 	$scope.creates = null;

 	//Liked wisdom that shall be shown on UI
 	$scope.likes = null;

 	//Set timeFormatService to $scope so that we can use it from UI part.
 	$scope.timeFormatService = timeFormatService;

 	$scope.userColor = null;

 	$scope.userName = userDataHolder.getUserName();

 	//Keep creative color generator to generate thumbnail color from UI
 	$scope.colorGenerator = creativeColorGenerateService;

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
 				$scope.creates = response.params.created;
 				$scope.likes = response.params.liked;
 			});
 		}
 	};


}]);
