wisdomApp.controller('messageOperationController',
 ['$scope', 
 'log',
 'modeService',
 function($scope, log, modeService){
 	log.d("SigninController");

 	var DISPLAY = true;
 	var NOT_DISPLAY = false;

	//TODO need to consider how we handle delete action
 	$scope.saveArray = [];

 	$scope.initialize = function()
 	{
 		$scope.panelCondition = NOT_DISPLAY;
 		$scope.titleState = DISPLAY;
 		$scope.descriptionState = NOT_DISPLAY;
 	};


 	$scope.operateMessage = function()
 	{
 		log.d("operateMessage");
 	};

 	$scope.onMouseOver = function()
 	{
 		log.d("onMouseOver");
 		$scope.panelCondition = DISPLAY;
 	};

 	$scope.onMouseLeave = function()
 	{
 		log.d("onMouseLeave");
 		$scope.panelCondition = NOT_DISPLAY;
 	};

 	$scope.showTitleField = function()
 	{
 		log.d("showTitleField");
 		$scope.titleState = DISPLAY;
 		$scope.descriptionState = NOT_DISPLAY;
 	};

 	$scope.showDescriptionField = function()
 	{
 		log.d("showDescriptionField");
 		$scope.titleState = NOT_DISPLAY;
 		$scope.descriptionState = DISPLAY;
 	};

 	$scope.saveSubTitleTexts = function(input)
 	{
 		log.d("saveSubTitleTexts: " + input);
 		if(input !== null){
	 		var str = '{"text": "' +input +'"}';
	 		$scope.saveArray.push(JSON.parse(str));
	 	}
 	};

 	$scope.saveSubDescriptionTexts = function(input)
 	{
 		log.d("saveSubDescriptionTexts: " + input);
 		if(input !== null){
	 		var str = '{"text": "' +input +'"}';
	 		$scope.saveArray.push(JSON.parse(str));
 		}
 	};


}]);
