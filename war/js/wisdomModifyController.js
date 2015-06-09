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

  // var DISPLAY_TITLE = true;
  // var DISPLAY_DESCRIPTION= false;

  var TYPE_TITLE = 1;
  var TYPE_MESSAGE = 2;

  var DEFAULT = 0;
  var NEW_TITLE_INPUT = 1;
  var NEW_DESCRIPTION_INPUT = 2;
  var TEXT_INPUT_DONE = 3;
  var TEXT_MODIFY = 4;

  $scope.WISDOM_TITLE_COUNT = Constants.WISDOM_TITLE_COUNT;

  $scope.WISDOM_TAG_MIN_COUNT = Constants.WISDOM_TAG_MIN_COUNT;
  $scope.WISDOM_TAG_MAX_COUNT = Constants.WISDOM_TAG_MAX_COUNT;

  $scope.WISDOM_DESCRIPTION_COUNT = Constants.WISDOM_DESCRIPTION_COUNT;

  $scope.WISDOM_SUB_TITLE_MAX_LENGTH = Constants.WISDOM_TITLE_COUNT;

  $scope.WISDOM_SUB_DESC_MAX_LENGTH = Constants.WISDOM_DESCRIPTION_COUNT;

  $scope.STATE = DEFAULT;

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
    // $scope.title = $scope.wisdom.title;
    // log.d("title: " + $scope.wisdom.title);
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
    // if($scope.panelCondition[index] === true){
    //   return true;
    // }
    // return false;
    if(modifyingItemNum === index){
      if($scope.isMessageModifyStable() === true){
       return  true;
      } else {
        return false;
      }
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

    //Change state
    $scope.STATE = TEXT_MODIFY;

    modifyingItemNum = index;

  };


  $scope.hideMessageInputArea = function(index)
  {
    log.d("hideMessageInputArea");
    modifyingItemNum = -1;
    $scope.changeStateToStable();
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

    $scope.changeStateToStable();

  };


  $scope.onMouseOver = function(index)
  {
    if($scope.isStateStable() === true){      
      $scope.panelCondition[index] = true;
    }
  };

  $scope.onMouseLeave = function(index)
  {
    $scope.panelCondition[index] = false;
  };

  $scope.isFloatingPanelVisible = function(index)
  {
    if($scope.panelCondition[index] === true){
      return true;
    }
    return false;
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

  $scope.showTitleField = function()
  {
    $scope.changeStateToInputtingNewTitle();
    $scope.STATE = NEW_TITLE_INPUT;
    // $scope.defaultMssageInputVisible = true;
  };

  $scope.showDescriptionField = function()
  {
    $scope.changeStateToInputtingNewDescription();
    $scope.STATE = NEW_DESCRIPTION_INPUT;
    // $scope.defaultMssageInputVisible = true;
  };

  $scope.isTitleFieldDisplay = function()
  {
    if($scope.STATE === NEW_TITLE_INPUT){
      return true;
    }
    return false;
  };

  $scope.isDescriptionFieldDisplay = function()
  {
    if($scope.STATE === NEW_DESCRIPTION_INPUT){
      return true;
    }
    return false;
  };

  $scope.changeStateToInputtingNewTitle = function()
  {
    $scope.STATE = NEW_TITLE_INPUT;
    $scope.messageModifyField = '';
  };

  $scope.changeStateToInputtingNewDescription = function()
  {
    $scope.STATE = NEW_DESCRIPTION_INPUT;
    $scope.messageModifyField = '';
  };

    $scope.changeStateToModifyingText = function()
  {
    $scope.STATE = TEXT_MODIFY;
    $scope.messageField = '';
  };

  $scope.hideDefaultMessageInputArea = function()
  {
    // $scope.defaultMssageInputVisible = false;
    $scope.messageField = '';
    $scope.changeStateToStable();
    $scope.optionalDescField = '';
    $scope.optionalHeadlineField = '';
  };

  $scope.saveMessageTexts = function(input)
  {
    log.d("saveMessageTexts: " + input);
    if(input !== null && input !== undefined){
      var type = input.type;
      var str = null;

      var current = timeService.getCurrentTime();

      if($scope.STATE === NEW_TITLE_INPUT){
        str = '{"message": "' +input +'", "tag": ' + TYPE_TITLE +  ', "updated_date": ' + current + '}';
        log.d("###################: " + str);
        $scope.messages.push(JSON.parse(str));
        $scope.optionalHeadlineField = '';
      } else if($scope.STATE === NEW_DESCRIPTION_INPUT){
        str = '{"message": "' +input +'", "tag": ' + TYPE_MESSAGE + ', "updated_date": ' + current + '}';
        log.d("###################: " + str);
        $scope.messages.push(JSON.parse(str));
        $scope.optionalDescField = '';
      }

      // createWisdomSharedStateService.shareInputMessages($scope.messages);

      //Prepare flag for modify
      // var index = $scope.messages.length-1;
      // $scope.modifyMessageCondition[index] = false;

    }
    $scope.changeStateToStable();
  };

  $scope.isStateStable = function()
  {
    if($scope.STATE === DEFAULT){
      return true;
    }
    return false;
  };

  $scope.isMessageModifyStable = function()
  {
    if($scope.STATE === TEXT_MODIFY){
      return true;
    }
    return false;
  };

  $scope.changeStateToStable = function()
  {
    $scope.STATE = DEFAULT;
    // if($scope.messages.length !== 0){
    //   $scope.STATE = TEXT_INPUT_DONE;
    // } else {
    //   $scope.STATE = DEFAULT;
    // }

    $scope.messageField = '';
    $scope.messageModifyField = '';

  };

  $scope.isHeadlineSaveButtonDisabled = function()
  {
    if($scope.optionalHeadlineField !== undefined){
      if($scope.optionalHeadlineField.length === 0 || $scope.optionalHeadlineField.length >= $scope.WISDOM_SUB_TITLE_MAX_LENGTH){
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  };

  $scope.isDescriptionSaveButtonDisabled = function()
  {
    if($scope.optionalDescField !== undefined){
      if($scope.optionalDescField.length === 0 || $scope.optionalDescField.length >= $scope.WISDOM_SUB_DESC_MAX_LENGTH){
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  };

  $scope.findCssClassbyType = function(data)
  {
    if(data.tag == TYPE_TITLE) {
      return "panel-heading";
    } else if (data.tag == TYPE_MESSAGE){
      return "panel-body";
    } else {
      // log.d("Unknown type.");
      return "panel-body";
    }
  };

}]);