wisdomApp.factory('timeService', ['log',
	function(log){
		return {
		 	getCurrentTime : function()
		 	{
		 		log.d("getCurrenttIme");
		 		var date = new Date();
				return date.getMilliseconds();
		 	}
		};
	}
]);
