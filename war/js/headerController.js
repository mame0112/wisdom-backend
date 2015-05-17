wisdomApp.controller('headerController',
 ['$scope', 
 '$http', 
 'log', 
 'modeService', 
 'Constants', 
 'userDataHolder',
 'dataRetriveService', 
 'searchAPIService',
 '$cookieStore',
 '$window',
 '$location',
 '$state',
'toasterService',
'$translate',
 function(
 	$scope, 
 	$http, 
 	log, 
 	modeService, 
 	Constants, 
 	userDataHolder, 
 	dataRetriveService, 
 	searchAPIService, 
 	$cookieStore, 
 	$window,
 	$location,
 	$state,
 	toasterService,
 	$translate
 	){

 	log.d("headerController");

 	$scope.navbarCollapsed = true;

 	$scope.userId = Constants.NO_USER;

	log.d("header directive initialize");
	$scope.userId = userDataHolder.getUserId();
	log.d("userId: " + $scope.userId);

	$scope.$on('$viewContentLoaded', function() {
		log.d("viewContentLoaded");
	});

	// var signout_done;
	// var signout_title;

	// $scope.initialize = function()
	// {
	// 	log.d("aaaaaaaaaaaaa");
	// $translate([
	// 	'signout_done',
	// 	'signout_title',
	// 	])
	// .then(function (translations) {
	// 	signout_done = translations['header.signout_done'];
	// 	signout_title = translations['header.signout_title'];
	// });
	// };

	$scope.toggled = function(open) {
		log.d('Dropdown is now: ', open);
	};

	$scope.onCreatePageSelect = function(){
		if(userDataHolder.getUserData() === null || userDataHolder.getUserData() === undefined){
			$state.go('signin');
		}
	};

 	$scope.onSigninOptionSelect = function (){
 		// log.d("onSigninOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNIN_PAGE);
 	};

 	$scope.onSignupOptionSelect = function (){
 		// log.d("onSignupOptionSelect");
		modeService.changeCurrentMode(Constants.STATE.STATE_SIGNUP_PAGE);
 	};

 	$scope.onUserInfoSelect = function (){
 		// log.d("onUserInfoSelect");
		// modeService.changeCurrentMode(Constants.STATE.STATE_SIGNUP_PAGE);
 	};

 	$scope.onNotificationSelect = function (){
 		// log.d("onNotificationSelect");
		// modeService.changeCurrentMode(Constants.STATE.STATE_SIGNUP_PAGE);
 	};

 	$scope.isSigninVisible = function (){
 		// log.d("isSigninVisible");
 		var data = userDataHolder.getUserData();
 		if(data === null|| data === undefined){
 			return true;
 		}
 		return false;
 	};

 	$scope.isSignupVisible = function (){
 		// log.d("isSignupVisible");
 		var data = userDataHolder.getUserData();
 		if(data === null || data === undefined){
 			return true;
 		}
 		return false;
 	};

 	$scope.isUserInfoVisible = function (){
 		// log.d("isUserInfoVisible");
 		var data = userDataHolder.getUserData();
 		if(data !== null && data !== undefined){
 			return true;
 		}
 		return false;
 	};

  	$scope.isNotificationVisible = function (){
 		// log.d("isNotificationVisible");
 		var data = userDataHolder.getUserData();
 		if(data !== null && data !== undefined){
 			return true;
 		}
 		return false;
 	};

  // 	$scope.isCreatePageVisible = function (){
 	// 	// log.d("isCreatePageVisible");
 	// 	var data = userDataHolder.getUserData();
 	// 	if(data !== null && data !== undefined){
 	// 		return true;
 	// 	}
 	// 	return false;
 	// };

 	$scope.initialize = function() {

 		var tmp = $cookieStore.get("name");
		log.d("tmp: " + tmp);


 		// log.d("initialize");
 		var data =  userDataHolder.getUserData();
 		if(data !== null && data !== undefined){
	 		$scope.userData = data;
			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_LOGIN);
	 	} else {
			modeService.changeCurrentMode(Constants.STATE.STATE_HOME_NO_LOGIN);
	 	}
 	};

 	$scope.searchWisdom = function(data) {

 		log.d("data: " + data);

 		$scope.searchParam = "";

 		$state.go('search', {result : data});

 		// log.d("searchWisdom");
 		// if(data !== null && data !== undefined){
	 	// 	// log.d("input value: " + data);
	 	// 	searchAPIService.search({searchParam : data}, function(response){
	 	// 		log.d("response received: " + response.params[0].title);
	 	// 		if(response !== null && response !== undefined){
	 	// 			// log.d("params: " + response);
	 	// 			// $scope.searchResult = response.params;
	 	// 			// $scope.params = response.params[0];
		 // 			// $state.go('search', { params: JSON.stringify(response)});
		 // 			$state.go('search', {result : response});
	 	// 		} else {

	 	// 		}


	 	// 		// $location.path('/new').search('x=y');
	 	// 		// $window.location.href = '/tos';
	 	// 	});
 		// }
 	};

 	$scope.getUserName = function()
 	{
 		return userDataHolder.getUserName();
 		// if(userDataHolder.getUserName() !== null && userDataHolder.getUserData() !== undefined){
	 	// 	log.d("userName " + userDataHolder.getUserData().userId);
	 	// 	return userDataHolder.getUserData().userName;
 		// }
 	};

 	$scope.getUserId = function()
 	{
 		return userDataHolder.getUserId();
 	};

 	$scope.signout = function()
 	{
 		userDataHolder.removeUserData();
	 	$scope.userId = Constants.NO_USER;
 		$state.go('/');
		toasterService.showSuccessToasterShort(signout_title, signout_done);
 	};

}]);
