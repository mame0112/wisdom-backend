wisdomApp.controller('SignupController', ['$scope', '$routeParams', 'log',  '$window',
 function($scope, $routeParams, log, $window){
 	log.d("SignupController");
 	// $scope.userId = $routeParams.userId;
 	// log.d("userId: " + $scope.userId);

 	$scope.twitterCheck = function()
 	{
 		log.d("twitterCheck");
 		$window.location.href = '/controller/twitter';
	 	// var api = new twitterAPIService();
	 	// api.$account(function(){
	 	// 	log.d("API called");
 		// });
 	};

}]);
