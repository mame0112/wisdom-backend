wisdomApp.controller('SignupController', 
	['$scope', 
	'$routeParams', 
	'log',  
	'$window', 
	'twitterAPIService', 
	'dataRetriveService', 
	'Constants',
	'modeService',
 function($scope, $routeParams, log, $window, twitterAPIService, dataRetriveService, Constants, modeService){
 	log.d("SignupController");
 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);

 	$scope.twitterSignup = function()
 	{
 		log.d("twitterSignup");
 		$window.location.href = '/controller/twitterSignup';
	 	// var api = new twitterAPIService();
	 	// api.$account(function(){
	 	// 	log.d("API called");
 		// });
 	};

 	$scope.twitterSignin = function()
 	{
 		log.d("twitterSignin");
	 	var api = new twitterAPIService();
	 	api.$account(function(data){
	 		log.d("API called");
	 		$scope.userData = data;
	 		var userId = dataRetriveService.getUserId(data);
	 		if(userId !== null && userId !== Constants.NO_USER){
	 			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_LOGIN);
		 		$window.location.href = '/';
	 		}
 		});
 	};

	$scope.$on(Constants.MODE_CHNAGE, function(event, param){
 		log.d("Mode changed: " + param);
 	});

 	$scope.getTwitterName = function(data)
 	{
 		log.d("getTwitterName");
 		return dataRetriveService.getTwitterName(data);

 	};

 	$scope.getPoint = function(data)
 	{
 		log.d("getPoint");
 		return dataRetriveService.getPoint(data);
 	};

 	$scope.getTwitterTokenSecret = function(data)
 	{
 		log.d("getTwitterTokenSecret");
 		return dataRetriveService.getTwitterTokenSecret(data);
 	};

 	 $scope.getThumbnail = function(data)
 	{
 		log.d("getThumbnail");
 		return dataRetriveService.getThumbnail(data);
 	};

  	 $scope.getLogindate = function(data)
 	{
 		log.d("getLogindate");
 		return dataRetriveService.getLogindate(data);
 	};

  	 $scope.getUserId = function(data)
 	{
 		log.d("getUserId");
 		return dataRetriveService.getUserId(data);
 	};

   	 $scope.getTwitterToken = function(data)
 	{
 		log.d("getTwitterToken");
 		return dataRetriveService.getTwitterToken(data);
 	};



}]);
