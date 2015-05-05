wisdomApp.controller('rankingController', 
['$scope', 
'log', 
'userRankingAPIService',
'timeService',
'timeFormatService',
function($scope, log, userRankingAPIService, timeService, timeFormatService){
 	log.d("rankingController");

 	$scope.wisdoms = null;

 	var limit = 10;

 	var param = {
 		"limit" : limit
 	};

 	$scope.initialize = function()
 	{
 		var now = timeService.getCurrentTime();
 		$scope.today = timeFormatService.getFormattedToday(now);
 	};

 	userRankingAPIService.status({servlet_params : param}, function(response){
 		log.d("user ranking received");

 		$scope.response = response;

 		//Set data under "params"
 		$scope.scores = response.params;
 	});


}]);