wisdomApp.controller('SigninController',
 ['$scope', 
 'log',
 'modeService',
 'userDataHolder',
 'twitterAPIService',
 'dataRetriveService',
 'Constants',
 '$window',
 '$cookies',
 'userLoginAPIService',
 'toasterService',
 '$state',
 '$translate',
 function(
 	$scope, 
 	log, 
 	modeService, 
 	userDataHolder, 
 	twitterAPIService, 
 	dataRetriveService, 
 	Constants, 
 	$window, 
 	$cookies, 
	userLoginAPIService,
	toasterService,
	$state,
	$translate){

 	log.d("SigninController");

	var toaster_title;
	var toaster_success_desc;
	var toaster_fail_desc;

	var account = $cookies.name;
	log.d("account: " + account);

 	var userName = null;
 	var password = null;

 	var params = {
 		"name":userName,
 		"password":password,
 	};

 	$scope.initialize = function()
 	{
		$translate([
			'signin.toaster_title',
			'signin.toaster_success_desc',
			'signin.toaster_fail_desc'
			])
		.then(function (translations) {
			toaster_title = translations['signin.toaster_title'];
			toaster_success_desc = translations['signin.toaster_success_desc'];
			toaster_fail_desc = translations['signin.toaster_fail_desc'];
		});
 	};

 	$scope.twitterSignin = function()
 	{
 		log.d("twitterSignin");
		twitterAPIService.$account(function(data){
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

 	$scope.usernameSignin = function()
 	{
 		log.d("usernameSignin");

 		if((params.name !== null && params.password !== null) && (params.name !== undefined && params.password !== undefined)){
	 		userLoginAPIService.login({servlet_params : params}, function(response){
		 		log.d("API response received");
		 		if(response !== null && response !== undefined){
		 			if(response.params.length !== 0){
				 		$scope.param = response.params;
				 		log.d("userId: " + $scope.param.userId);
				 		userDataHolder.setUserData($scope.param);
						toasterService.showSuccessToasterShort(toaster_title, toaster_success_desc);
						//TODO Need to log in
						$state.go('/');
		 			} else {
						// toasterService.showErrorToasterLong("Signin", "Failed to sign in. Please check user name and password");
						toasterService.showErrorToasterLong(toaster_title, toaster_fail_desc);
			 			log.d("response is null or undefined");
		 			}
	 			} else {
					toasterService.showErrorToasterLong(toaster_title, toaster_fail_desc);
		 			log.d("response is null or undefined");
	 			}
	 		});
 		} else {

 		}
 	};

	$scope.$watch('name', function(newValue, oldValue) {
		params.name = newValue;
	});

	$scope.$watch('password', function(newValue, oldValue) {
		params.password = newValue;
	});

}]);
