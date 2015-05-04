wisdomApp.controller('wisdomModifyController', 
['$scope', 
'log', 
'modifyWisdomAPIService',
'$stateParams',
'modeService', 
'Constants', 
'timeFormatService',
'creativeColorGenerateService',
'$sce',
'$state',
'toasterService',
'userDataHolder',
function($scope, log, modifyWisdomAPIService, $stateParams, modeService, Constants, timeFormatService, creativeColorGenerateService, $sce, $state, toasterService, userDataHolder){
 	log.d("wisdomDetailController");

 	var input = $stateParams.currentWisdom;

  var userData = userDataHolder.getUserData();

  var userId = null;

  var params = {
    "wisdom":$scope.wisdom,
    "userId": userId,
  };

  if(userData !== null && userData !== undefined){
    params.userId = userData.userId;
  } else {
    log.d("not logged in");
  }

 	if(input !== null && input !== undefined) {
	 	$scope.wisdom = angular.fromJson(input);
	 	// $scope.messages = $scope.wisdom.messages;

	 	$scope.messages = JSON.parse($scope.wisdom.messages);
    $scope.wisdom.messages = $scope.messages;

    params.wisdom = $scope.wisdom;

	 	$scope.colorGenerater = creativeColorGenerateService;

  } else {
 		//Error handling
    toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
    $state.go('/');
	}

  	$scope.cancelModification = function()
  	{
  		log.d("cancelModification");
      $state.go('wisdom', {wisdomId : $scope.wisdom.id});
  	};

  	$scope.saveModification = function()
  	{
      modifyWisdomAPIService.modifywisdom({servlet_params : params}, function(response){
        log.d("response received");
        if(response !== null && response !== undefined && response.params !== null && response.params !== undefined){
          if(response.params.length !== 0){
            var param = response.params[0];
            var wisdomId = param.id;
            var point = param.point;
            toasterService.showSuccessToasterLong("Modify wisdom", "Wisdom is successfully modified. Your point is now " + point +" point");
            $state.go('wisdom', {wisdomId : wisdomId});
          } else {
            log.d("wrong length");
            toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
          }
        } else {
            log.d("Response is null or undefined");
            toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
        }
      });
  	};
}]);