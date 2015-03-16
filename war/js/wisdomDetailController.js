wisdomApp.controller('wisdomDetailController', 
['$scope', 
'log', 
'wisdomAPIService',
'$routeParams',
'modeService', 
'Constants', 
function($scope, log, wisdomAPIService, $routeParams, modeService, Constants){
 	log.d("wisdomDetailController");

 	var wisdomId = $routeParams.wisdomId;
 	log.d("wisdomId: " + wisdomId);

 	$scope.noContent = null;

 	$scope.initialize = function(){
 		log.d("wisdomDetailController initialize");
		modeService.changeCurrentMode(Constants.STATE.STATE_WISDOM_PAGE);
 	};

 	// $scope.wisdoms = null;

	//Get target wisdom
 	wisdomAPIService.wisdom({servlet_wisdom_id : wisdomId}, function(response){
 		log.d("response received");

 		//Set data under "params"
 		$scope.wisdom = response.params;
 		if($scope.wisdom !== null && $scope.wisdom !== undefined){
 			if($scope.wisdom.messages !== null && $scope.wisdom.messages !== undefined){
	 			log.d("AAAA: " + $scope.wisdom.messages);
		 		$scope.messages = JSON.parse(response.params.messages);
 			} else {
				//Error handling 
 			}
 		} else {
 			//Error handling
 		}

 		// log.d("response:" + $scope.wisdom.messages);
	});

}]);