wisdomApp.controller('messageOperationController',
 ['$scope', 
 'log',
 'modeService',
 'createWisdomSharedStateService',
 function($scope, log, modeService, createWisdomSharedStateService){
 	log.d("messageOperationController");

 	var DISPLAY_TITLE = true;
 	var DISPLAY_DESCRIPTION= false;

 	var TYPE_TITLE = 1;
 	var TYPE_MESSAGE = 2;

 	$scope.panelCondition = {};



	//TODO need to consider how we handle delete action
 	$scope.saveArray = [];

 	$scope.initialize = function()
 	{
 		for(i=0; i<$scope.panelCondition.length; i++){
 		$scope.panelCondition[i] = false;
 		}

  		$scope.messageState = DISPLAY_TITLE;
 	};


 	$scope.operateMessage = function()
 	{
 		log.d("operateMessage");
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

 	$scope.showTitleField = function()
 	{
 		log.d("showTitleField");
 		$scope.messageState = DISPLAY_TITLE; 		
 	};

 	$scope.showDescriptionField = function()
 	{
 		log.d("showDescriptionField");
 		$scope.messageState = DISPLAY_DESCRIPTION;
 	};

 	$scope.modidyInputMessage = function(data)
 	{
 		log.d("modidyInputMessage");
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

 	$scope.moveUpMessagePosition= function(data)
 	{
 		log.d("moveUpMessagePosition");
 	};

 	$scope.moveDownMessagePosition= function(data)
 	{
 		log.d("moveDownMessagePosition");
 	};

 	$scope.saveMessageTexts = function(input)
 	{
 		log.d("saveMessageTexts: " + input);
 		if(input !== null){
 			var type = input.type;
 			var str = null;

 			if($scope.messageState === DISPLAY_TITLE){
		 		str = '{"entry": "' +input +'", "type": ' + TYPE_TITLE + '}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			} else {
		 		str = '{"entry": "' +input +'", "type": ' + TYPE_MESSAGE + '}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			}

 			createWisdomSharedStateService.shareInputMessages($scope.saveArray);
 			$scope.messageField = '';


 		}
 	};

 	$scope.getMessageType = function(data)
 	{
 		log.d("isDescription");
 		if(data.type === TYPE_TITLE)
 		{
	 		log.d("This is title");
 			return "panel-heading";
 		} else if (data.type === TYPE_MESSAGE){
	 		log.d("This is description");
 			return "panel-body";
 		} else {
 			log.d("Unknown type.");
 			return "panel-body";
 		}
 	};

}]);
