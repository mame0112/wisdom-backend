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
'$state',
'userDataHolder',
function($scope, 
	log, 
	wisdomAPIService, 
	$stateParams, 
	modeService, 
	Constants, 
	timeFormatService, 
	creativeColorGenerateService, 
	$sce, 
	$state, 
	userDataHolder){
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
	 		// $scope.wisdom = response.params;
	 		$scope.wisdom = response.params;
	 		

	 		if($scope.wisdom !== null && $scope.wisdom !== undefined){
	 			if($scope.wisdom.messages !== null && $scope.wisdom.messages !== undefined){
	 				// var fileURL = URL.createObjectURL($scope.wisdom.thumbnail);
	 				// $scope.content = $sce.trustAsResourceUrl(fileURL);
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

	$scope.modifyWisdom = function() 
	{
		log.d("modifyWisdom");
		var param = angular.toJson($scope.wisdom, false);
		// $state.go('modifywisdom', {wisdom : $scope.wisdom);
		// $state.go('modifywisdom', {currentWisdom : $scope.wisdom});
		$state.go('modifywisdom', {currentWisdom : param});
	};

	$scope.isSignedIn = function()
	{
		var userData = userDataHolder.getUserData();
		if(userData !== null && userData !== undefined){
			return true;
		} else {
			return false;
		}

	};

	$scope.createAccount = function()
	{
		log.d("createAccount");
		$state.go('signup');
	};

}]);