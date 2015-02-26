wisdomApp.controller('breadcrumbController', ['$scope', '$http', 'log', 'modeService', 'Constants',function($scope, $http, log, modeService, Constants){
 	log.d("breadcrumbController");
	// modeService.changeCurrentMode();

 	$scope.$on('mode_changed', function(event, param){
 		log.d("breadcrumbController Mode changed: " + param);
 	});

 	$scope.isBreadcrumbVisible = function (){
 		return modeService.getCurrentMode() === Constants.STATE.STATE_HOME_NO_LOGIN || 
 		modeService.getCurrentMode() === Constants.STATE.STATE_HOME_LOGIN;
 	};

}]);
