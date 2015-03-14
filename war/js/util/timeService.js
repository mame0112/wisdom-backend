wisdomApp.factory('timeService', ['log',
	function(log){
		return {
		 	getCurrentTime : function()
		 	{
		 		var date = new Date();
				return date.getTime();
		 	}
		};
	}
]);
