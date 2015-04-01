wisdomApp.controller('pickupController', 
['$scope', 
'log', 
'latestAPIService',
'timeService',
'timeFormatService',
'creativeColorGenerateService',
function($scope, log, latestAPIService, timeService, timeFormatService, creativeColorGenerateService){
 	log.d("pickupController");

 	$scope.wisdoms = null;

 	$scope.creativeColorGenerateService = creativeColorGenerateService;

 	var offset = 0;

 	var param = {
 		"offset" : offset
 	};

 	$scope.initialize = function()
 	{
 		var now = timeService.getCurrentTime();
 		$scope.today = timeFormatService.getFormattedToday(now);

 	};

 	latestAPIService.latest({servlet_params : param}, function(response){
 		log.d("latest received");

 		$scope.response = response;

 		//Set data under "params"
 		$scope.wisdoms = response.params;
 	});


}]);