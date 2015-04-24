wisdomApp.controller('wisdomDetailController', 
['$scope', 
'log', 
'wisdomAPIService',
'$stateParams',
'modeService', 
'Constants', 
'timeFormatService',
'creativeColorGenerateService',
'$sce',
function($scope, log, wisdomAPIService, $stateParams, modeService, Constants, timeFormatService, creativeColorGenerateService, $sce){
 	log.d("wisdomDetailController");

 	var wisdomId = $stateParams.wisdomId;
 	log.d("wisdomId: " + wisdomId);

 	$scope.noContent = null;

 	$scope.colorGenerater = creativeColorGenerateService;

 	$scope.initialize = function(){
 		log.d("wisdomDetailController initialize");
		modeService.changeCurrentMode(Constants.STATE.STATE_WISDOM_PAGE);

		//Get target wisdom
	 	wisdomAPIService.wisdom({servlet_wisdom_id : wisdomId}, function(response){
	 		log.d("response received");

	 		//Set data under "params"
	 		$scope.wisdom = response.params;
	 		if($scope.wisdom !== null && $scope.wisdom !== undefined){
	 			if($scope.wisdom.messages !== null && $scope.wisdom.messages !== undefined){
	 				// var fileURL = URL.createObjectURL($scope.wisdom.thumbnail);
	 				// $scope.content = $sce.trustAsResourceUrl(fileURL);
		 			// log.d("AAAA1: " + $scope.wisdom.thumbnail);
		 			// log.d("AAAA2: " + fileURL);
			 		$scope.messages = JSON.parse(response.params.messages);
	 			} else {
					//Error handling 
	 			}
	 		} else {
	 			//Error handling
	 		}

	 		// log.d("response:" + $scope.wisdom.messages);
		});

 	};

 	$scope.formatTimeInfo = function(date){
 		//If date information is correct, we return formatted date
 		if(date !== null && date !== undefined && date !== 0){
 			return timeFormatService.getFormattedTime(date);
 		}
 	};

 	$scope.isValidDateFormat = function(date){
 		if(date !== null && date !== undefined && date !== 0){
 			return true;
 		}
 		return false;
	};

}]);