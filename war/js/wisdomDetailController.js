wisdomApp.controller('wisdomDetailController', 
['$scope', 
'log', 
'wisdomAPIService',
'$routeParams',
function($scope, log, wisdomAPIService, $routeParams){
 	log.d("wisdomDetailController");

 	var wisdomId = $routeParams.wisdomId;
 	log.d("wisdomId: " + wisdomId);

 	// $scope.wisdoms = null;

	//Get target wisdom
 	wisdomAPIService.wisdom({servlet_wisdom_id : wisdomId}, function(response){
 		log.d("response received");

 		//Set data under "params"
 		$scope.wisdom = response.params;
 		log.d("response:" + $scope.wisdom.tag);
	});

 	// latestAPIService.latest(function(response){
 	// 	log.d("latest received");

 	// 	//Set data under "params"
 	// 	$scope.wisdoms = response.params;
 	// });


	$scope.initialize = function()
	{
		log.d("initialize");

	};

}]);