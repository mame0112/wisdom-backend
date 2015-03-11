wisdomApp.controller('messageOperationController',
 ['$scope', 
 'log',
 'modeService',
 function($scope, log, modeService){
 	log.d("SigninController");

 	var DISPLAY_TITLE = true;
 	var DISPLAY_DESCRIPTION= false;

	//TODO need to consider how we handle delete action
 	$scope.saveArray = [];

 	$scope.initialize = function()
 	{
 		$scope.panelCondition = DISPLAY_DESCRIPTION;
 		$scope.messageState = DISPLAY_TITLE;
 	};


 	$scope.operateMessage = function()
 	{
 		log.d("operateMessage");
 	};

 	$scope.onMouseOver = function()
 	{
 		log.d("onMouseOver");
 		$scope.panelCondition = DISPLAY_TITLE;
 	};

 	$scope.onMouseLeave = function()
 	{
 		log.d("onMouseLeave");
 		$scope.panelCondition = DISPLAY_DESCRIPTION;
 	};

 	$scope.showTitleField = function()
 	{
 		log.d("showTitleField");
 		$scope.titleState = DISPLAY_TITLE;
 		$scope.descriptionState = DISPLAY_DESCRIPTION;
 	};

 	$scope.showDescriptionField = function()
 	{
 		log.d("showDescriptionField");
 		$scope.titleState = DISPLAY_DESCRIPTION;
 		$scope.descriptionState = DISPLAY_TITLE;
 		$scope.messageState = DISPLAY_DESCRIPTION;
 	};

 	$scope.saveMessageTexts = function(input)
 	{
 		log.d("saveMessageTexts: " + input);
 		if(input !== null){
 			var type = input.type;
 			var str = null;

 			if($scope.messageState === DISPLAY_TITLE){
		 		str = '{"text": "' +input +'", "type": "title"}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			} else {
		 		str = '{"text": "' +input +'", "type": "description"}';
		 		log.d("###################: " + str);
		 		$scope.saveArray.push(JSON.parse(str));
 			}

 		}
 	};



}]);
