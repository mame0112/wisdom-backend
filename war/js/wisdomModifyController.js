wisdomApp.controller('wisdomModifyController', 
['$scope', 
'log', 
'modifyWisdomAPIService',
'$stateParams',
'modeService', 
'Constants', 
'timeFormatService',
'creativeColorGenerateService',
'$sce',
'$state',
'toasterService',
'userDataHolder',
'timeService',
'Constants',
function($scope,
 log, 
 modifyWisdomAPIService, 
 $stateParams, modeService, 
 Constants, 
 timeFormatService, 
 creativeColorGenerateService, 
 $sce, 
 $state, 
 toasterService, 
 userDataHolder, 
 timeService,
 Constants){
 	log.d("wisdomModifyController");

  var DISPLAY_TITLE = true;
  var DISPLAY_DESCRIPTION= false;

  var TYPE_TITLE = 1;
  var TYPE_MESSAGE = 2;

  var DEFAULT = 0;
  var NEW_TEXT_INPUT = 1;
  var TEXT_INPUT_DONE = 2;
  var TEXT_MODIFY = 3;

  $scope.WISDOM_TAG_MIN_COUNT = Constants.WISDOM_TAG_MIN_COUNT;
  $scope.WISDOM_TAG_MAX_COUNT = Constants.WISDOM_TAG_MAX_COUNT;

 	var input = $stateParams.currentWisdom;

  var userData = userDataHolder.getUserData();

  var userId = null;


  var modifyingItemNum = -1;

  var params = {
    "wisdom":$scope.wisdom,
    "userId": userId
    // "title":$scope.title,
    // "tag":$scope.tag,
    // "description":$scope.description
  };

  $scope.panelCondition = {};

  if(userData !== null && userData !== undefined){
    params.userId = userData.userId;
  } else {
    log.d("not logged in");
  }

 	if(input !== null && input !== undefined) {
	 	$scope.wisdom = angular.fromJson(input);
	 	// $scope.messages = $scope.wisdom.messages;

	 	$scope.messages = JSON.parse($scope.wisdom.messages);
    // $scope.wisdom.messages = $scope.messages;

    params.wisdom = $scope.wisdom;

	 	$scope.colorGenerater = creativeColorGenerateService;

  } else {
 		//Error handling
    toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
    $state.go('/');
	}

  $scope.initialize = function()
  {
    //Initialize state
    // $scope.changeStateToStable();

    var messageNum = $scope.messages.length;
    
    // $scope.STATE = DEFAULT;
    for(i=0; i<messageNum; i++)
    {
      $scope.panelCondition[i] = false;
      // $scope.modifyMessageCondition[i] = false;
    }

    //   $scope.messageState = DISPLAY_TITLE;

  };

  $scope.changeModifyingItem = function(index){
    modifyingItemNum = index;
  };

  $scope.isOriginalMessageDisplay = function(index){
    if(modifyingItemNum === index){
      return  false;
    } else {
      return true;
    }
  };

  $scope.isModifyPanelVisible = function(index){
    if(modifyingItemNum === index){
      return  true;
    } else {
      return false;
    }
  };


  $scope.moveUpMessagePosition= function(array, index)
  {
    log.d("moveUpMessagePosition");
    if(index !== 0)
    {
      array.splice(index-1, 2, array[index], array[index-1]);
    }
  };

  $scope.moveDownMessagePosition= function(array, index)
  {
    if(array !== null && array.length !== 0)
    {
      array.splice(index, 2, array[index+1], array[index]);
    }

  };

  $scope.moveMessageUpToTop = function(array, index)
  {
    if(array !== null && array.length !== 0)
    {
      var targetItem = array[index];

      //Remove item from array
      array.splice(index, 1);

      //Add item to top
      array.splice(0, 0, targetItem);

    }
  };

  $scope.moveMessageDownToBottom = function(array, index)
  {
    log.d("moveMessageDownToBottom");
    if(array !== null && array.length !== 0)
    {
      var targetItem = array[index];

      log.d("targetItem: " + targetItem);

      //Remove item from array
      array.splice(index, 1);

      //Add item to top
      array.splice(array.length, 0, targetItem);
    }
  };

  $scope.deleteInputMessage = function(data)
  {
    log.d("deleteInputMessage");
    for(i = 0; i<$scope.messages.length; i++){
    log.d("loop: " + data.message);
      if($scope.messages[i].message == data.message){
        $scope.messages.splice(i, 1);
      }
    }
  };

  $scope.modifyInputMessage = function(data, index)
  {
    log.d("modifyInputMessage: " + data.tag);

    var text = data.message;
    $scope.messageModifyField = text;

    modifyingItemNum = index;

  };


  $scope.hideMessageInputArea = function(index)
  {
    log.d("hideMessageInputArea");
    modifyingItemNum = -1;
  };

  $scope.saveModifiedTexts = function(original, newMessage, index)
  {
    log.d("saveModifiedTexts: " + original + " / " + newMessage);
    if(original !== null && newMessage !== null){
      var type = original.type;
      var oldText = original.message;
      var str = null;

      var result = {
        "message": newMessage,
        "updated_date":timeService.getCurrentTime(),
        "update_userid":userId,
        "like_num":original.like_num,
        "itemId":original.itemId,
        "tag":original.tag
      };


      for(i = 0; i<$scope.messages.length; i++){
        if($scope.messages[i].message == oldText){
          $scope.messages.splice(i, 1, result);
        }
      }

      modifyingItemNum = -1;

    }
  };


  $scope.onMouseOver = function(index)
  {
      $scope.panelCondition[index] = true;
  };

  $scope.onMouseLeave = function(index)
  {
    $scope.panelCondition[index] = false;
  };

	$scope.cancelModification = function()
	{
		log.d("cancelModification");
    $state.go('wisdom', {wisdomId : $scope.wisdom.id});
	};

	$scope.saveModification = function()
	{
    // log.d("saveModification: " + $scope.wisdom.title);
    //Parse Json Object to Json string
    params.wisdom.messages = angular.fromJson($scope.messages);

    modifyWisdomAPIService.modifywisdom({servlet_params : params}, function(response){
      log.d("response received");
      if(response !== null && response !== undefined && response.params !== null && response.params !== undefined){
        if(response.params.length !== 0){
          var param = response.params[0];
          var wisdomId = param.id;
          var point = param.point;
          toasterService.showSuccessToasterLong("Modify wisdom", "Wisdom is successfully modified. Your point is now " + point +" point");
          $state.go('wisdom', {wisdomId : wisdomId});
        } else {
          log.d("wrong length");
          toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
        }
      } else {
          log.d("Response is null or undefined");
          toasterService.showErrorToasterShort("Modify wisdom", "Something went wrong. Please try again later");
      }
    });
	};


}]);