wisdomApp.controller('pickupController', 
['$scope', 
'log', 
'latestAPIService',
function($scope, log, latestAPIService){
 	log.d("pickupController");

 	$scope.wisdoms = null;

 	latestAPIService.latest(function(response){
 		log.d("latest received");

 		//Set data under "params"
 		$scope.wisdoms = response.params;
 	});


}]);