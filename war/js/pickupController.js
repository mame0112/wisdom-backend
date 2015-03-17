wisdomApp.controller('pickupController', 
['$scope', 
'log', 
'latestAPIService',
function($scope, log, latestAPIService){
 	log.d("pickupController");

 	$scope.wisdoms = null;

 	var offset = 0;

 	var param = {
 		"offset" : offset
 	};

 	latestAPIService.latest({servlet_params : param}, function(response){
 		log.d("latest received");

 		$scope.response = response;

 		//Set data under "params"
 		$scope.wisdoms = response.params;
 	});


}]);