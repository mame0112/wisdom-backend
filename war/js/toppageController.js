wisdomApp.controller('toppageController',
 ['log',
 'modeService',
 'Constants',
 'userDataHolder',
 '$scope',
 function(log, modeService, Constants, userDataHolder, $scope){
 	log.d("toppageController");
	// modeService.changeCurrentMode();

 	$scope.initialize = function()
 	{
 		log.d("initialize");
 		var data = userDataHolder.getUserData();
 		if(data !== null && data !== undefined){
			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_LOGIN);
 		} else {
			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_NO_LOGIN);
 		}

 	};

}]);
