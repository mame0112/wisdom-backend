wisdomApp.controller('SigninController',
 ['$scope', 
 'log',
 'modeService',
 'userDataHolder',
 'twitterAPIService',
 'dataRetriveService',
 'Constants',
 '$window',
 function($scope, log, modeService, userDataHolder, twitterAPIService, dataRetriveService, Constants, $window){
 	log.d("SigninController");

 	$scope.twitterSignin = function()
 	{
 		log.d("twitterSignin");
	 	var api = new twitterAPIService();
	 	api.$account(function(data){
	 		log.d("API called");
	 		$scope.userData = data;
	 		var userId = dataRetriveService.getUserId(data);
	 		if(userId !== null && userId !== Constants.NO_USER){
	 			userDataHolder.setUserData(data);
	 			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_LOGIN);
		 		$window.location.href = '/';
	 		}
 		});
 	};

}]);
