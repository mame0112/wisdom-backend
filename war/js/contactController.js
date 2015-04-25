wisdomApp.controller('contactController', 
['$scope', 
'log', 
'latestAPIService',
function($scope, log, latestAPIService, timeService, timeFormatService, creativeColorGenerateService){
 	log.d("contactController");

 	$scope.initialize = function()
 	{
 		log.d("initialize");
 	};

}]);