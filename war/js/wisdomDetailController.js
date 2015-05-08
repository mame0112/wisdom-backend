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
	toasterService){
 	log.d("wisdomDetailController");

 	var wisdomId = $stateParams.wisdomId;
 	log.d("wisdomId: " + wisdomId);

 	$scope.noContent = null;

 	$scope.colorGenerater = creativeColorGenerateService;

 	// Variable to identify if the user already select "Like"
 	// (We should prevent the user to continuous "Like" press)
 	var isLiked = [];

 	$scope.likeImage = [];

 	$scope.initialize = function(){
 		log.d("wisdomDetailController initialize");
		modeService.changeCurrentMode(Constants.STATE.STATE_WISDOM_PAGE);

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
						toasterService.showSuccessToasterShort("Point updated", "Your point is now " + point);
					} else {
						//Error handling
						toasterService.showErrorToasterShort("Error", "Failed to do 'Like'. Please try again later");
					}
				} else {
					//Error handling
					toasterService.showErrorToasterShort("Error", "Failed to do 'Like'. Please try again later");
				}
	 		});
		}

	};

}]);