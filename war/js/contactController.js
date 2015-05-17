wisdomApp.controller('contactController', 
['$scope', 
'log', 
'contactAPIService',
'toasterService',
'$state',
'$translate',
function($scope, log, contactAPIService, toasterService, $state, $translate){
 	log.d("contactController");

 	var name = null;
 	var email = null;
 	var message = null;

 	var message_send_result = null;
 	var successfully_sent = null;
 	var send_failed = null;

 	var param = {
 		"name": name,
 		"email": email,
 		"message": message
 	};

 	$scope.initialize = function()
 	{
 		log.d("initialize");

		$translate([
			'contact.message_send_result',
			'contact.successfully_sent',
			'contact.send_failed'
			])
		.then(function (translations) {
			message_send_result = translations['contact.message_send_result'];
			successfully_sent = translations['contact.successfully_sent'];
			send_failed = translations['contact.send_failed'];
		});

 	};

 	$scope.sendMessage = function()
 	{
 		log.d("sendMessage");
 		log.d("result/ " + " name: " + param.name + " email: " + param.email + " message: " + param.message);

		contactAPIService.mail({servlet_params : param}, function(response){
	 		log.d("latest received");

	 		if(response !== null && response !== undefined){
				toasterService.showSuccessToasterLong(message_send_result, successfully_sent);
				$state.go('/');
	 		} else {
	 			//Error handling
				toasterService.showErrorToasterLong(message_send_result, send_failed);
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