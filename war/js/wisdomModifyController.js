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
function($scope, log, modifyWisdomAPIService, $stateParams, modeService, Constants, timeFormatService, creativeColorGenerateService, $sce, $state, toasterService, userDataHolder){
 	log.d("wisdomModifyController");

  var DISPLAY_TITLE = true;
  var DISPLAY_DESCRIPTION= false;

  var TYPE_TITLE = 1;
  var TYPE_MESSAGE = 2;

  var DEFAULT = 0;
  var NEW_TEXT_INPUT = 1;
  var TEXT_INPUT_DONE = 2;
  var TEXT_MODIFY = 3;

 	var input = $stateParams.currentWisdom;

  var userData = userDataHolder.getUserData();

  var userId = null;

  // Parameter to show modify text area and buttons
  // $scope.modifyMessageCondition = {};


  var modifyingItemNum = -1;

  var params = {
    "wisdom":$scope.wisdom,
    "userId": userId,
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
    //Dismiss default text area
    // $scope.defaultMssageInputVisible = false;

    //Show modify text area
    // $scope.messageModifyFieldVisible = true;

    //Set flag on for target modify panel
    // $scope.modifyMessageCondition[index] = true;

    // $scope.changeStateToModifyingText();

  };


  $scope.hideMessageInputArea = function(index)
  {
    log.d("hideMessageInputArea");
    modifyingItemNum = -1;
    // $scope.modifyMessageCondition[index] = false;

    // $scope.changeStateToStable();
    // $scope.defaultMssageInputVisible= false;
    // $scope.messageModifyFieldVisible= false;
  };

  $scope.saveModifiedTexts = function(original, newMessage, index)
  {
    log.d("saveModifiedTexts: " + original + " / " + newMessage);
    if(original !== null && newMessage !== null){
      var type = original.type;
      var oldText = original.message;
      var str = null;

      log.d("oldText: " + oldText);
      // log.d("newText: " + $scope.messageModifyField);

      str = '{"message": "' + newMessage +'", "tag": ' + original.tag + '}';
      log.d("###################: " + str);

      // $scope.saveArray.push(JSON.parse(str));

      for(i = 0; i<$scope.messages.length; i++){
        if($scope.messages[i].message == oldText){
          $scope.messages.splice(i, 1, JSON.parse(str));
        }
      }

      //Set flag for showing modify panel for target item
      // $scope.modifyMessageCondition[index] = false;

      modifyingItemNum = -1;

      // createWisdomSharedStateService.shareInputMessages($scope.messages);

    }
    // $scope.changeStateToStable();
  };


  $scope.onMouseOver = function(index)
  {
    log.d("onMouseOver");
    // if($scope.isMessageModifyingState() === false){
      $scope.panelCondition[index] = true;
    // }

    // $scope.panelCondition = DISPLAY_TITLE;
  };

  $scope.onMouseLeave = function(index)
  {
    log.d("onMouseLeave");
    $scope.panelCondition[index] = false;
    // $scope.panelCondition = DISPLAY_DESCRIPTION;
  };

	$scope.cancelModification = function()
	{
		log.d("cancelModification");
    $state.go('wisdom', {wisdomId : $scope.wisdom.id});
	};

	$scope.saveModification = function()
	{
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


 //  $scope.onMouseOver = function(index)
 //  {

 //    if($scope.isMessageModifyingState() === false){
 //      $scope.panelCondition[index] = true;
 //    }

 //    // $scope.panelCondition = DISPLAY_TITLE;
 //  };

 //  $scope.onMouseLeave = function(index)
 //  {
 //    $scope.panelCondition[index] = false;
 //    // $scope.panelCondition = DISPLAY_DESCRIPTION;
 //  };

 //  $scope.modifyInputMessage = function(data, index)
 //  {
 //    log.d("modifyInputMessage: " + data.tag);
 //    // $scope.messageField = data.entry;
 //    // switch(data.tag){
 //    //   case DISPLAY_TITLE:
 //    //     $scope.modifyMessageState = DISPLAY_TITLE;
 //    //   break;
 //    //   case DISPLAY_DESCRIPTION:
 //    //     $scope.modifyMessageState = DISPLAY_DESCRIPTION;
 //    //   break;
 //    //   default:
 //    //     log.d("unknwon type");
 //    //     $scope.modifyMessageState = DISPLAY_DESCRIPTION;
 //    //   break;
 //    // }
 //    var text = data.message;
 //    $scope.messageModifyField = text;
 //    //Dismiss default text area
 //    // $scope.defaultMssageInputVisible = false;

 //    //Show modify text area
 //    // $scope.messageModifyFieldVisible = true;

 //    //Set flag on for target modify panel
 //    $scope.modifyMessageCondition[index] = true;

 //    $scope.changeStateToModifyingText();

 //  };

 //  $scope.moveUpMessagePosition= function(array, index)
 //  {
 //    log.d("moveUpMessagePosition");
 //    if(index !== 0)
 //    {
 //      array.splice(index-1, 2, array[index], array[index-1]);
 //    }
 //  };

 //  $scope.moveDownMessagePosition= function(array, index)
 //  {
 //    if(array !== null && array.length !== 0)
 //    {
 //      array.splice(index, 2, array[index+1], array[index]);
 //    }

 //  };

 //  $scope.moveMessageUpToTop = function(array, index)
 //  {
 //    if(array !== null && array.length !== 0)
 //    {
 //      var targetItem = array[index];

 //      //Remove item from array
 //      array.splice(index, 1);

 //      //Add item to top
 //      array.splice(0, 0, targetItem);

 //    }
 //  };

 //  $scope.moveMessageDownToBottom = function(array, index)
 //  {
 //    log.d("moveMessageDownToBottom");
 //    if(array !== null && array.length !== 0)
 //    {
 //      var targetItem = array[index];

 //      log.d("targetItem: " + targetItem);

 //      //Remove item from array
 //      array.splice(index, 1);

 //      //Add item to top
 //      array.splice(array.length, 0, targetItem);
 //    }
 //  };

 //  $scope.deleteInputMessage = function(data)
 //  {
 //    log.d("deleteInputMessage");
 //    for(i = 0; i<$scope.messages.length; i++){
 //    log.d("loop: " + data.message);
 //      if($scope.messages[i].message == data.message){
 //        $scope.messages.splice(i, 1);
 //      }
 //    }
 //  };

 //  $scope.isOriginalMessageDisplayed = function(index)
 //  {
 //    if($scope.modifyMessageCondition[index] === false){
 //      return true;
 //    }

 //    return false;
 //    // if($scope.isMessageModifyingState() === false)
 //    // {
 //    //   return true;
 //    // }
 //    //  else {
 //    //   if($scope.modifyMessageCondition[index] === false)
 //    //   {
 //    //     return true;
 //    //   }
 //    // }
 //    // return false;
 //  };

 //  $scope.isModifyPanelVisible = function(index)
 //  {
 //    // if($scope.modifyMessageCondition[index] === true){
 //    //   return false;
 //    // }

 //    // return true;
 //    if($scope.isMessageModifyingState() === true)
 //    {
 //      if($scope.modifyMessageCondition[index] === true)
 //      {
 //        return true;
 //      }
 //    }
 //    return false;
 //  };

 //  $scope.hideMessageInputArea = function(index)
 //  {
 //    log.d("hideMessageInputArea");
 //    $scope.modifyMessageCondition[index] = false;

 //    $scope.changeStateToStable();
 //    // $scope.defaultMssageInputVisible= false;
 //    // $scope.messageModifyFieldVisible= false;
 //  };

 //  $scope.saveModifiedTexts = function(original, newMessage, index)
 //  {
 //    log.d("saveModifiedTexts: " + original + " / " + newMessage);
 //    if(original !== null && newMessage !== null){
 //      var type = original.type;
 //      var oldText = original.message;
 //      var str = null;

 //      log.d("oldText: " + oldText);
 //      // log.d("newText: " + $scope.messageModifyField);

 //      str = '{"entry": "' + newMessage +'", "type": ' + original.type + '}';
 //      log.d("###################: " + str);

 //      // $scope.saveArray.push(JSON.parse(str));

 //      for(i = 0; i<$scope.messages.length; i++){
 //        if($scope.messages[i].message == oldText){
 //          $scope.messages.splice(i, 1, JSON.parse(str));
 //        }
 //      }

 //      //Set flag for showing modify panel for target item
 //      $scope.modifyMessageCondition[index] = false;

 //      createWisdomSharedStateService.shareInputMessages($scope.messages);

 //    }
 //    $scope.changeStateToStable();
 //  };


 //  $scope.isMessageModifyingState = function()
 //  {
 //    if($scope.STATE === TEXT_MODIFY){
 //      return true;
 //    } else {
 //      return false;
 //    }
 //  };

 //  //-------- Below is state handling ---------//

 //  $scope.isDefaultState = function()
 //  {
 //    if($scope.STATE === DEFAULT || $scope.STATE === TEXT_INPUT_DONE){
 //      return true;
 //    } else {
 //      return false;
 //    }
 //  };

 //    $scope.isInputtingNewMessageState = function()
 //  {
 //    if($scope.STATE === NEW_TEXT_INPUT){
 //      return true;
 //    } else {
 //      return false;
 //    }
 //  };

 //    $scope.isMessageModifyingState = function()
 //  {
 //    if($scope.STATE === TEXT_MODIFY){
 //      return true;
 //    } else {
 //      return false;
 //    }
 //  };

 //  $scope.changeStateToStable = function()
 //  {
 //    if($scope.messages.length !== 0){
 //      $scope.STATE = TEXT_INPUT_DONE;
 //    } else {
 //      $scope.STATE = DEFAULT;
 //    }

 //    $scope.messageField = '';
 //    $scope.messageModifyField = '';

 //  };

 //  $scope.changeStateToInputtingNewText = function()
 //  {
 //    $scope.STATE = NEW_TEXT_INPUT;
 //    $scope.messageModifyField = '';
 //  };

 //    $scope.changeStateToModifyingText = function()
 //  {
 //    $scope.STATE = TEXT_MODIFY;
 //    $scope.messageField = '';
 //  };

}]);