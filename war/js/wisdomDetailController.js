wisdomApp.controller('wisdomDetailController', 
['$scope', 
'log', 
'wisdomAPIService',
'$routeParams',
function($scope, log, wisdomAPIService, $routeParams){
 	log.d("wisdomDetailController");

 	$scope.wisdomId = $routeParams.wisdomId;
 	log.d("wisdomId: " + $scope.wisdomId);

 	// $scope.wisdoms = null;

	//Get target wisdom
 	wisdomAPIService.wisdom({servlet_resp_id: 1, param: '@servlet_new_wisdom_param'}, function(response){
 		log.d("response received");

 		//Set data under "params"
 		$scope.wisdoms = response.params;
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