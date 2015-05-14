wisdomApp.controller('messageOperationController',
 ['$scope', 
 'log',
 'modeService',
 'createWisdomSharedStateService',
 'timeService',
 'Constants',
  '$translate',
 function($scope, log, modeService, createWisdomSharedStateService, timeService, Constants, $translate){
 	log.d("messageOperationController");

 	var DISPLAY_TITLE = true;
 	var DISPLAY_DESCRIPTION= false;

 	var TYPE_TITLE = 1;
 	var TYPE_MESSAGE = 2;

 	var DEFAULT = 0;
 	var NEW_TEXT_INPUT = 1;
 	var TEXT_INPUT_DONE = 2;
 	var TEXT_MODIFY = 3;

	$scope.subContentLength = Constants.WISDOM_TITLE_COUNT;

 	$scope.STATE = DEFAULT;

 	// Parameter to show floating panel
 	$scope.panelCondition = {};

 	// Parameter to show modify text area and buttons
 	$scope.modifyMessageCondition = {};



	//TODO need to consider how we handle delete action
 	$scope.saveArray = [];

 	$scope.initialize = function()
 	{
 		//Initialize state
		$scope.changeStateToStable();
 		
	 	$scope.STATE = DEFAULT;
 		for(i=0; i<$scope.panelCondition.length; i++)
 		{
	 		$scope.panelCondition[i] = false;
 		}

 		for(i=0; i<$scope.modifyMessageCondition.length; i++)
 		{
 			$scope.modifyMessageCondition[i] = false;
 		}


  		$scope.messageState = DISPLAY_TITLE;
 	};


 	$scope.showTitleField = function()
 	{
 		$scope.changeStateToInputtingNewText();
 		$scope.messageState = DISPLAY_TITLE;
 		// $scope.defaultMssageInputVisible = true;
 	};

 	$scope.showDescriptionField = function()
 	{
 		$scope.changeStateToInputtingNewText();
 		$scope.messageState = DISPLAY_DESCRIPTION;
 		// $scope.defaultMssageInputVisible = true;
 	};

 	// $scope.isMessageInputVisible = function()
 	// {
 	// 	log.d("isMessageInputVisible");
 	// 	if($scope.defaultMssageInputVisible === true){
 	// 		return true;
 	// 	} else {
 	// 		return false;
 	// 	}
 	// };


 	$scope.saveMessageTexts = function(input)
 	{
 		log.d("saveMessageTexts: " + input);
 		if(input !== null && input !== undefined){
 			var type = input.type;
 			var str = null;

 			var current = timeService.getCurrentTime();

 			if($scope.messageState === DISPLAY_TITLE){
		 		str = '{"entry": "' +input +'", "type": ' + TYPE_TITLE +  ', "updated_date": ' + current + '}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			} else {
		 		str = '{"entry": "' +input +'", "type": ' + TYPE_MESSAGE + ', "updated_date": ' + current + '}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			}

 			createWisdomSharedStateService.shareInputMessages($scope.saveArray);

 			//Prepare flag for modify
 			var index = $scope.saveArray.length-1;
			$scope.modifyMessageCondition[index] = false;

 		}
		$scope.changeStateToStable();
 	};

 	$scope.saveModifiedTexts = function(original, newMessage, index)
 	{
 		log.d("saveModifiedTexts: " + original + " / " + newMessage);
 		if(original !== null && newMessage !== null){
 			var type = original.type;
 			var oldText = original.entry;
 			var str = null;

	 		log.d("oldText: " + oldText);
	 		// log.d("newText: " + $scope.messageModifyField);

	 		str = '{"entry": "' + newMessage +'", "type": ' + original.type + '}';
	 		log.d("###################: " + str);

	 		// $scope.saveArray.push(JSON.parse(str));

	 		for(i = 0; i<$scope.saveArray.length; i++){
 				if($scope.saveArray[i].entry == oldText){
					$scope.saveArray.splice(i, 1, JSON.parse(str));
 				}
	 		}

	 		//Set flag for showing modify panel for target item
	 		$scope.modifyMessageCondition[index] = false;

 			createWisdomSharedStateService.shareInputMessages($scope.saveArray);

 		}
		$scope.changeStateToStable();
 	};

 	// $scope.getMessageType = function(data){
 	// 	log.d("getMessageType");

 	// 	if(data !== null){
 	// 		switch(data.type)
 	// 		{
 	// 			case YPE_TITLE:
 	// 			break;
 	// 			case TYPE_MESSAGE:
 	// 			break;
 	// 			default:
 	// 			break;
 	// 		}
 	// 	}
 	// 	if(data.type === TYPE_TITLE)
 	// 	{
	 // 		log.d("This is title");
 	// 		return "panel-heading";
 	// 	} else if (data.type === TYPE_MESSAGE){
	 // 		log.d("This is description");
 	// 		return "panel-body";
 	// 	} else {
 	// 		log.d("Unknown type.");
 	// 		return "panel-body";
 	// 	}
 	// };

 	$scope.findCssClassbyType = function(data)
 	{
 		if(data.type == TYPE_TITLE) {
 			return "panel-heading";
 		} else if (data.type == TYPE_MESSAGE){
 			return "panel-body";
 		} else {
 			log.d("Unknown type.");
 			return "panel-body";
 		}
 	};

 	$scope.onMouseOver = function(index)
 	{
 		log.d("onMouseOver");
 		$scope.panelCondition[index] = true;
 		// $scope.panelCondition = DISPLAY_TITLE;
 	};

 	$scope.onMouseLeave = function(index)
 	{
 		log.d("onMouseLeave");
 		$scope.panelCondition[index] = false;
 		// $scope.panelCondition = DISPLAY_DESCRIPTION;
 	};

  	$scope.deleteInputMessage = function(data)
 	{
 		log.d("deleteInputMessage");
 		for(i = 0; i<$scope.saveArray.length; i++){
 		log.d("loop: " + data.entry);
 			if($scope.saveArray[i].entry == data.entry){
				$scope.saveArray.splice(i, 1);
 			}
 		}
 	};

 	$scope.modifyInputMessage = function(data, index)
 	{
 		// $scope.messageField = data.entry;
 		switch(data.type){
 			case DISPLAY_TITLE:
 				$scope.modifyMessageState = DISPLAY_TITLE;
 			break;
 			case DISPLAY_DESCRIPTION:
	 			$scope.modifyMessageState = DISPLAY_DESCRIPTION;
 			break;
 			default:
 				log.d("unknwon type");
	 			$scope.modifyMessageState = DISPLAY_DESCRIPTION;
 			break;
 		}
		var text = data.entry;
		$scope.messageModifyField = text;
		//Dismiss default text area
		// $scope.defaultMssageInputVisible = false;

		//Show modify text area
		// $scope.messageModifyFieldVisible = true;

		//Set flag on for target modify panel
		$scope.modifyMessageCondition[index] = true;

		$scope.changeStateToModifyingText();

 	};

 	$scope.hideMessageInputArea = function(index)
 	{

		$scope.modifyMessageCondition[index] = false;

 		$scope.changeStateToStable();
		// $scope.defaultMssageInputVisible= false;
		// $scope.messageModifyFieldVisible= false;
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

 	$scope.hideDefaultMessageInputArea = function()
 	{
 		// $scope.defaultMssageInputVisible = false;
		$scope.messageField = '';
		$scope.changeStateToStable();
 	};

 	$scope.isModifyPanelVisible = function(index)
 	{

 		if($scope.isMessageModifyingState() === true)
 		{
 			if($scope.modifyMessageCondition[index] === true)
 			{
	 			return true;
 			}
 		}
 		return false;
 	};

 	$scope.isOriginalMessageDisplayed = function(index)
 	{
 		if($scope.isMessageModifyingState() === false)
 		{
 			return true;
 		} else {
			if($scope.modifyMessageCondition[index] === false)
			{
				return true;
			}
 		}
 		return false;
 	};

 	//-------- Below is state handling ---------//

 	$scope.isDefaultState = function()
 	{
 		if($scope.STATE === DEFAULT || $scope.STATE === TEXT_INPUT_DONE){
 			return true;
 		} else {
 			return false;
 		}
 	};

   	$scope.isInputtingNewMessageState = function()
 	{
 		if($scope.STATE === NEW_TEXT_INPUT){
 			return true;
 		} else {
 			return false;
 		}
 	};

  	$scope.isMessageModifyingState = function()
 	{
 		if($scope.STATE === TEXT_MODIFY){
 			return true;
 		} else {
 			return false;
 		}
 	};

 	$scope.changeStateToStable = function()
 	{
		if($scope.saveArray.length !== 0){
	 		$scope.STATE = TEXT_INPUT_DONE;
		} else {
	 		$scope.STATE = DEFAULT;
		}

		$scope.messageField = '';
		$scope.messageModifyField = '';

 	};

 	$scope.changeStateToInputtingNewText = function()
 	{
 		$scope.STATE = NEW_TEXT_INPUT;
		$scope.messageModifyField = '';
 	};

   	$scope.changeStateToModifyingText = function()
 	{
 		$scope.STATE = TEXT_MODIFY;
		$scope.messageField = '';
 	};



}]);
