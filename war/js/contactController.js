wisdomApp.controller('contactController', 
['$scope', 
'log', 
'contactAPIService',
'toasterService',
function($scope, log, contactAPIService, timeService, timeFormatService, creativeColorGenerateService, toasterService){
 	log.d("contactController");

 	var name = null;
 	var email = null;
 	var message = null;

 	var param = {
 		"name": name,
 		"email": email,
 		"message": message
 	};

 	$scope.initialize = function()
 	{
 		toasterService.showSuccessToasterShort("Contact", "Message successfully sent!");
 		log.d("initialize");
 	};

 	$scope.sendMessage = function()
 	{
 		log.d("sendMessage");
 		log.d("result/ " + " name: " + param.name + " email: " + param.email + " message: " + param.message);

		contactAPIService.mail({servlet_params : param}, function(response){
	 		log.d("latest received");

	 		if(response !== null && response !== undefined){
				toasterService.showSuccessToasterShort("Contact", "Message successfully sent!");
	 		} else {
	 			//Error handling
				toasterService.showSuccessToasterShort("Contact", "Failed to send message. Please try again");
	 		}

	 	});
	};

 	$scope.$watch('name', function(newValue, oldValue) {
		param.name = newValue;
	});

 	$scope.$watch('email', function(newValue, oldValue) {
		param.email = newValue;
	});

 	$scope.$watch('message', function(newValue, oldValue) {
		param.message = newValue;
	});

}]);