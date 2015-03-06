wisdomApp.controller('SignupController', ['$scope', '$routeParams', 'log',  '$window', 'twitterAPIService', 'dataRetriveService',
 function($scope, $routeParams, log, $window, twitterAPIService, dataRetriveService){
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

 		});
 	};

 	$scope.getTwitterName = function(data)
 	{
 		log.d("getTwitterName");
 		return dataRetriveService.getTwitterName(data);

 	};



}]);
