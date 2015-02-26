wisdomApp.controller('headerController', ['$scope', '$http', 'log', 'modeService', 'Constants', function($scope, $http, log, modeService, Constants){
 	log.d("headerController");

 	$scope.onSigninOptionSelect = function (){
 		log.d("onSigninOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNIN_PAGE);
 	};

 	$scope.onSignupOptionSelect = function (){
 		log.d("onSignupOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNUP_PAGE);
 	};

}]);
