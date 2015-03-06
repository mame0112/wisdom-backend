wisdomApp.factory('dataRetriveService', ['log',
 function(log) {
 	return {
	 	getTwitterName : function(data)
	 	{
	 		log.d("getTwitterName");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.twitterName;
	 		}
	 		return null;
		}
 	};
 }
]);