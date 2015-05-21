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
 '$auth',
 '$facebook',
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
	$translate,
	$auth,
	$facebook){

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

	$scope.authenticate = function(provider) {
		log.d("authenticate");
		$auth.authenticate(provider);
	};

 	$scope.twitterSignin = function()
 	{
 		log.d("twitterSignin");
		twitterAPIService.account(function(data){
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

 	$scope.isNameSubmitButtonDisable = function()
 	{
 		if($scope.name !== undefined && $scope.password !== undefined){
 			if($scope.name.length >= 6 && $scope.password.length >= 6){ 
 				return false;
 			}
 		}

 		return true;

 	};

 	$scope.usernameSignin = function()
 	{
 		log.d("usernameSignin");

 		if((params.name !== null && params.password !== null) && (params.name !== undefined && params.password !== undefined)){
	 		userLoginAPIService.login({servlet_params : params}, function(response){
		 		log.d("API response received");
		 		if(response !== null && response !== undefined){
			 		$scope.param = response.params;
		 			if($scope.param.length !== 0 && $scope.param.userId !== null && $scope.param.userId !== undefined){
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

  $scope.isLoggedIn = false;

  $scope.login = function() {
    $facebook.login().then( refresh );
  };
  $scope.logout = function() {
    $facebook.logout().then( refresh );
  };

  function refresh() {
    $facebook.api("/me").then( 
      function(response) {
      	log.d("Welcome: " + response.name);
      	log.d("Access token; " + response.authResponse.accessToken);
        // $scope.welcomeMsg = "Welcome " + response.name;
        $scope.isLoggedIn = true;
      },  
      function(err) {
      	log.d("Please log in");
        // $scope.welcomeMsg = "Please log in";
        $scope.isLoggedIn = false;
      }   
    );  

    $facebook.getLoginStatus().then(
      function(response){
      	log.d("response: " + response);
        console.log(response);
      },
      function(er){
      }
    );
  }

}]);


// This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      testAPI();
    } else if (response.status === 'not_authorized') {
      console.log("Please log into this app.");
      // The person is logged into Facebook, but not your app.
      // document.getElementById('status').innerHTML = 'Please log ' +
      //   'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      console.log("Please log into Facebook");
      // document.getElementById('status').innerHTML = 'Please log ' +
      //   'into Facebook.';
    }
  }


 // $scope.checkLoginState = function() {
 //    FB.getLoginStatus(function(response) {
 //      statusChangeCallback(response);
 //    });
 //  };

