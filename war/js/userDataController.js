wisdomApp.controller('UserDataController',
 ['$scope', 
 '$stateParams', 
 'userInfoAPIService',
 'log',
 'Constants',
 'timeFormatService',
 'creativeColorGenerateService',
 'userDataHolder',
 '$state',
 '$translate',
 'toasterService',
 function($scope,
  $stateParams,
   userInfoAPIService,
   log,
   Constants,
   timeFormatService,
   creativeColorGenerateService,
   userDataHolder,
   $state,
   $translate,
   toasterService){

 	console.log("UserDataController");

 	//TODO Need to fix in the future.
 	$scope.userId = $stateParams.userId;
 	// $scope.userId = 1;
 	console.log("userId: " + $scope.userId);

 	//Point info that shall be shown on UI
 	$scope.point = 0;

 	//Created wisdom that shall be shown on UI
 	$scope.creates = null;

 	//Liked wisdom that shall be shown on UI
 	$scope.likes = null;

 	//Set timeFormatService to $scope so that we can use it from UI part.
 	$scope.timeFormatService = timeFormatService;

 	$scope.userColor = null;

 	$scope.userName = userDataHolder.getUserName();

 	//Keep creative color generator to generate thumbnail color from UI
 	$scope.colorGenerator = creativeColorGenerateService;

 	var offset = 0;
 	var limit = 5;

 	var param = {
 		"userId" : $scope.userId,
 		"offset" : offset,
 		"limit" : limit
 	};

 	var error_title;
 	var error_illegal_data_desc;


 	$scope.initialize = function()
 	{
 		log.d("initialize");

 		//Load translation
		$translate([
			'userpage.error_title',
			'userpage.error_illegal_data_desc'
			])
		.then(function (translations) {
			error_title = translations['userpage.error_title'];
			error_illegal_data_desc = translations['userpage.error_illegal_data_desc'];
		});

 		$scope.loadUseData($scope.userId);
 		$scope.userColor = creativeColorGenerateService.generateColor($scope.userId);
 	};

 	$scope.loadUseData = function(userId)
 	{
 		if(userId !== null && userId !== Constants.NO_USER)
 		{
 			userInfoAPIService.status({servlet_params : param}, function(response){
 				log.d("response received");
 				if(response.params.userId !== Constants.NO_USER){
	 				$scope.point = response.params.point;
	 				$scope.creates = response.params.created;
	 				$scope.likes = response.params.liked;

	 				$scope.createdNum = 0;
					if($scope.creates !== null && $scope.creates !== undefined){
		 				$scope.createdNum = $scope.creates.length;
					}

					$scope.likedNum = 0;
					if($scope.likes !== null && $scope.likes !== undefined){
		 				$scope.likedNum = $scope.likes.length;
					}
 				} else {
 					//If returned user Id is illegal (NO_USER)
 					// This shall happens illegal user id is passed from browser address bar
 					log.d("Illegal data is returned");
 					toasterService.showErrorToasterShort(error_title, error_illegal_data_desc);
 					$state.go('/');
 				}


 			});
 		}
 	};


}]);
