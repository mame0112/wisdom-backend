wisdomApp.controller('wisdomDetailController', 
['$scope', 
'log', 
'wisdomAPIService',
'$stateParams',
'modeService', 
'Constants', 
'timeFormatService',
'creativeColorGenerateService',
'$sce',
'$state',
'userDataHolder',
'wisdomMessageLikeAPIService',
'toasterService',
'Page',
'$translate',
function($scope, 
	log, 
	wisdomAPIService, 
	$stateParams, 
	modeService, 
	Constants, 
	timeFormatService, 
	creativeColorGenerateService, 
	$sce, 
	$state, 
	userDataHolder,
	wisdomMessageLikeAPIService,
	toasterService,
	Page,
	$translate){
 	log.d("wisdomDetailController");

 	var wisdomId = $stateParams.wisdomId;
 	log.d("wisdomId: " + wisdomId);

 	$scope.noContent = null;

 	$scope.colorGenerater = creativeColorGenerateService;

 	var wisdom_update_title;
 	var wisdom_update_desc;
 	var wisdom_update_desc_no_user;
 	var like_error;
 	var like_failed;


 	// Variable to identify if the user already select "Like"
 	// (We should prevent the user to continuous "Like" press)
 	var isLiked = [];

 	$scope.likeImage = [];


 	$scope.initialize = function(){
 		log.d("wisdomDetailController initialize");

		modeService.changeCurrentMode(Constants.STATE.STATE_WISDOM_PAGE);

		//Setup translations
		$translate([
			'wisdom.wisdom_update_title',
			'wisdom.wisdom_update_desc',
			'wisdom.like_error',
			'wisdom.like_failed',
			'wisdom.wisdom_update_desc_point',
			'wisdom.wisdom_update_desc_no_user',
			])
		.then(function (translations) {
			wisdom_update_title = translations['wisdom.wisdom_update_title'];
			wisdom_update_desc = translations['wisdom.wisdom_update_desc'];
			wisdom_update_desc_point = translations['wisdom.wisdom_update_desc_point'];
			like_error = translations['wisdom.like_error'];
			like_failed = translations['wisdom.like_failed'];
			wisdom_update_desc_no_user = translations['wisdom.wisdom_update_desc_no_user'];
		});

		//Get target wisdom
	 	wisdomAPIService.wisdom({servlet_wisdom_id : wisdomId}, function(response){
	 		log.d("response received");

	 		//Set data under "params"
	 		// $scope.wisdom = response.params;
	 		$scope.wisdom = response.params;
	 		

	 		if($scope.wisdom !== null && $scope.wisdom !== undefined){
	 			if($scope.wisdom.messages !== null && $scope.wisdom.messages !== undefined){

	 				// var fileURL = URL.createObjectURL($scope.wisdom.thumbnail);
	 				// $scope.content = $sce.trustAsResourceUrl(fileURL);
			 		$scope.messages = JSON.parse(response.params.messages);

	 				for(var i=0; i< $scope.messages.length; i++){
	 					isLiked[i] = false;
	 					$scope.likeImage[i] = 'image/star_not_selected.png';
	 				}

	 			} else {
					//Error handling 
	 			}
	 		} else {
	 			//Error handling
	 		}

	 		// log.d("response:" + $scope.wisdom.messages);
		});

 	};

 	$scope.formatTimeInfo = function(date){
 		//If date information is correct, we return formatted date
 		if(date !== null && date !== undefined && date !== 0){
 			return timeFormatService.getFormattedTime(date);
 		}
 	};

 	$scope.isValidDateFormat = function(date){
 		if(date !== null && date !== undefined && date !== 0){
 			return true;
 		}
 		return false;
	};

	$scope.modifyWisdom = function() 
	{
		log.d("modifyWisdom");
		var param = angular.toJson($scope.wisdom, false);
		// $state.go('modifywisdom', {wisdom : $scope.wisdom);
		// $state.go('modifywisdom', {currentWisdom : $scope.wisdom});
		$state.go('modifywisdom', {currentWisdom : param});
	};

	$scope.isSignedIn = function()
	{
		var userData = userDataHolder.getUserData();
		if(userData !== null && userData !== undefined){
			return true;
		} else {
			return false;
		}

	};

	$scope.createAccount = function()
	{
		$state.go('signup');
	};

	$scope.likeButtonPress = function(index)
	{

		var userId = userDataHolder.getUserId();
		log.d("userId: " + userId);
		var messageId = index;

		var param = {
			'userId': userId,
			'id': wisdomId,
			'itemId': messageId
		};

		//If the user hasn't selected liked yet
		if(isLiked[index] === false){
			isLiked[index] = true;
			$scope.likeImage[index] = 'image/star_selected_material.png';

			$scope.messages[index].like_num += 1;
			wisdomMessageLikeAPIService.like({servlet_params : param}, function(response){
				if(response !== null && response !== undefined){
					if(response.params !== null && response.params !== undefined){
						var point = response.params.point;
						if(point !== Constants.NO_USER){
							toasterService.showSuccessToasterShort(wisdom_update_title, wisdom_update_desc + point + wisdom_update_desc_point);
						} else {
							toasterService.showSuccessToasterLong(wisdom_update_title, wisdom_update_desc_no_user);
						}

					} else {
						//Error handling
						toasterService.showErrorToasterShort(like_error, like_failed);
					}
				} else {
					//Error handling
					toasterService.showErrorToasterShort(like_error, like_failed);
				}
	 		});
		}

	};

}]);