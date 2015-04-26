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
 // '$state',
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
 	$state){
 	log.d("headerController");

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
