wisdomApp.controller('messageOperationController',
 ['$scope', 
 'log',
 'modeService',
 function($scope, log, modeService){
 	log.d("SigninController");

 	$scope.operateMessage = function()
 	{
 		log.d("operateMessage");
 	};

}]);
