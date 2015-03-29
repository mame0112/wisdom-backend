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
 '$state',
 function($scope, $http, log, modeService, Constants, userDataHolder, dataRetriveService, searchAPIService, $cookieStore, $state){
 	log.d("headerController");

	$scope.$state = $state;

	$scope.isStateRoot = function ()
	{
		if($state.current.name !== "privacypolicy" && $state.current.name !== "tos" && $state.current.name !== "contact"){
			return true;
		}
		return false;
	};

	$scope.isStatePrivacyPolicy = function ()
	{
		if($state.current.name == Constants.VIEW_STATE.PRIVACY_POLICY){
			return true;
		}
		return false;
	};

	$scope.isStateTos = function ()
	{
		if($state.current.name == Constants.VIEW_STATE.TOS){
			return true;
		}
		return false;
	};

	$scope.isStateContact = function ()
	{
		if($state.current.name == Constants.VIEW_STATE.CONTACT){
			return true;
		}
		return false;
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

  	$scope.isCreatePageVisible = function (){
 		// log.d("isCreatePageVisible");
 		var data = userDataHolder.getUserData();
 		if(data !== null && data !== undefined){
 			return true;
 		}
 		return false;
 	};

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
 		// log.d("searchWisdom");
 		if(data !== null && data !== undefined){
	 		// log.d("input value: " + data);
	 		searchAPIService.search({searchParam : data});
 		}
 	};

 	$scope.getTwitterName = function(data)
 	{
 		// log.d("getTwitterName");
 		return dataRetriveService.getTwitterName(data);
 	};

 	$scope.getPoint = function(data)
 	{
 		// log.d("getPoint");
 		return dataRetriveService.getPoint(data);
 	};

 	$scope.getTwitterTokenSecret = function(data)
 	{
 		// log.d("getTwitterTokenSecret");
 		return dataRetriveService.getTwitterTokenSecret(data);
 	};

 	 $scope.getThumbnail = function(data)
 	{
 		// log.d("getThumbnail: " + dataRetriveService.getThumbnail(data));
 		return dataRetriveService.getThumbnail(data);
 	};

  	 $scope.getLogindate = function(data)
 	{
 		// log.d("getLogindate");
 		return dataRetriveService.getLogindate(data);
 	};

  	 $scope.getUserId = function(data)
 	{
 		// log.d("getUserId");
 		return dataRetriveService.getUserId(data);
 	};

   	 $scope.getTwitterToken = function(data)
 	{
 		// log.d("getTwitterToken");
 		return dataRetriveService.getTwitterToken(data);
 	};


}]);
