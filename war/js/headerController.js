wisdomApp.controller('headerController',
 ['$scope', 
 '$http', 
 'log', 
 'modeService', 
 'Constants', 
 'userDataHolder',
 function($scope, $http, log, modeService, Constants, userDataHolder){
 	log.d("headerController");

 	$scope.onSigninOptionSelect = function (){
 		log.d("onSigninOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNIN_PAGE);
 	};

 	$scope.onSignupOptionSelect = function (){
 		log.d("onSignupOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNUP_PAGE);
 	};

 	$scope.isSigninVisible = function (){
 		log.d("isSigninVisible");
 		var data = userDataHolder.getUserData();
 		if(data === null){
 			return true;
 		}
 		return false;
 	};

 	$scope.isSignupVisible = function (){
 		log.d("isSignupVisible");
 		var data = userDataHolder.getUserData();
 		if(data === null){
 			return true;
 		}
 		return false;
 	};

 	$scope.isUserInfoVisible = function (){
 		log.d("isUserInfoVisible");
 		var data = userDataHolder.getUserData();
 		if(data !== null){
 			return true;
 		}
 		return false;
 	};

  	$scope.isNotificationVisible = function (){
 		log.d("isNotificationVisible");
 		var data = userDataHolder.getUserData();
 		if(data !== null){
 			return true;
 		}
 		return false;
 	};

}]);
