wisdomApp.controller('pickupController', 
['$scope', 
'log', 
'latestAPIService',
'timeService',
'timeFormatService',
function($scope, log, latestAPIService, timeService, timeFormatService){
 	log.d("pickupController");

 	$scope.wisdoms = null;

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