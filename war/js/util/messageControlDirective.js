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



	//TODO need to consider how we handle delete action
 	$scope.saveArray = [];

 	$scope.initialize = function()
 	{
 		$scope.panelCondition = false;
  		$scope.messageState = DISPLAY_TITLE;
 	};


 	$scope.operateMessage = function()
 	{
 		log.d("operateMessage");
 	};

 	$scope.onMouseOver = function()
 	{
 		log.d("onMouseOver");
 		$scope.panelCondition = true;
 		// $scope.panelCondition = DISPLAY_TITLE;
 	};

 	$scope.onMouseLeave = function()
 	{
 		log.d("onMouseLeave");
 		$scope.panelCondition = false;
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
