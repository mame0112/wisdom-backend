wisdomApp.controller('SignupController', 
	['$scope', 
	'$stateParams', 
	'log',  
	'$window', 
	'twitterAPIService', 
	'dataRetriveService', 
	'Constants',
	'modeService',
	'userDataHolder',
	'$cookieStore',
	'userNameAPIService',
	'toasterService',
	'$state',
 function(
 	$scope, 
 	$stateParams, 
 	log, 
 	$window, 
 	twitterAPIService, 
 	dataRetriveService, 
 	Constants, modeService, 
 	userDataHolder, 
 	$cookieStore, 
 	userNameAPIService,
 	toasterService,
 	$state){
 	log.d("SignupController");
 	// log.d("userId: " + $scope.userId);

 	var userName = null;
 	var password = null;
 	var mailAddress = null;

 	var params = {
 		"name":userName,
 		"password":password,
 		"mailAddress":mailAddress
 	};

 	$scope.initialize = function()
 	{
 		log.d("initialize");
		var name = $cookieStore.get('name');
		log.d("name: " + name);
 	};

 	$scope.twitterSignup = function()
 	{
 		log.d("twitterSignup");
 		$window.location.href = '/controller/twitterSignup';
	 	// var api = new twitterAPIService();
	 	// api.$account(function(){
	 	// 	log.d("API called");
 		// });
 	};

	$scope.$on(Constants.MODE_CHNAGE, function(event, param){
 		log.d("Mode changed: " + param);
 	});

 	$scope.getTwitterName = function(data)
 	{
 		log.d("getTwitterName");
 		return dataRetriveService.getTwitterName(data);
 	};

 	$scope.getPoint = function(data)
 	{
 		log.d("getPoint");
 		return dataRetriveService.getPoint(data);
 	};

 	$scope.getTwitterTokenSecret = function(data)
 	{
 		log.d("getTwitterTokenSecret");
 		return dataRetriveService.getTwitterTokenSecret(data);
 	};

 	 $scope.getThumbnail = function(data)
 	{
 		// log.d("getThumbnail");
 		return dataRetriveService.getThumbnail(data);
 	};

  	 $scope.getLogindate = function(data)
 	{
 		// log.d("getLogindate");
 		return dataRetriveService.getLogindate(data);
 	};

  	 $scope.getUserId = function(data)
 	{
 		log.d("getUserId");
 		return dataRetriveService.getUserId(data);
 	};

   	 $scope.getTwitterToken = function(data)
 	{
 		log.d("getTwitterToken");
 		return dataRetriveService.getTwitterToken(data);
 	};

 // 	FB.getLoginStatus(function(response) {
 //    statusChangeCallback(response);
	// });

	$scope.checkLoginState = function() {
		log.d("checkLoginState");
		FB.getLoginStatus(function(response) {
			statusChangeCallback(response);
		});
	};

	$scope.$watch('name', function(newValue, oldValue) {
		params.name = newValue;
	});

	$scope.$watch('password', function(newValue, oldValue) {
		params.password = newValue;
	});

	$scope.$watch('email', function(newValue, oldValue) {
		params.mailAddress = newValue;
	});

	$scope.createAccount = function()
	{
		log.d("createAccount");

		if(params.name !== null && params.password !== null && params.mailAddress !== null){

		 	userNameAPIService.useraccount({servlet_params : params}, function(response){
		 		log.d("User account response received");

		 		if(response !== null && response !== undefined){
		 			if(response.params.length !== 0)
		 			{
				 		$scope.param = response.params[0];
				 		log.d("userId: " + $scope.param.userId);
						toasterService.showSuccessToasterShort("Signup", "Account successfully created!");
						//TODO Need to log in
						$state.go('/');
		 			} else {
			 			log.d("response is null or undefined");
						toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		 			}
		 		} else {
		 			log.d("response is null or undefined");
					toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		 		}
		 		// $scope.wisdoms = response.params;
		 	});

		} else {
			log.d("Some input filed is not filled out yet");
			toasterService.showErrorToasterLong("Signup", "Please check if you fill out user name, password and mail address field correctly");
		}

	};

}]);

	statusChangeCallback = function(response) {
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
	      // The person is logged into Facebook, but not your app.
	      document.getElementById('status').innerHTML = 'Please log ' +
	        'into this app.';
	    } else {
	      // The person is not logged into Facebook, so we're not sure if
	      // they are logged into this app or not.
	      document.getElementById('status').innerHTML = 'Please log ' +
	        'into Facebook.';
	    }
	};

window.fbAsyncInit = function() {
  FB.init({
    appId      : '1459557601000808',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.2' // use version 2.2
  });
};

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));



