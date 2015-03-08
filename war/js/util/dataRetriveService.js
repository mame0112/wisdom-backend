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
		},
	 	getPoint : function(data)
	 	{
	 		log.d("getPoint");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.point;
	 		}
	 		return null;
		},
	 	getTwitterTokenSecret : function(data)
	 	{
	 		log.d("getTwitterTokenSecret");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.twitterTokenSecret;
	 		}
	 		return null;
		},
	 	getThumbnail : function(data)
	 	{
	 		// log.d("getThumbnail");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.thumbnail;
	 		}
	 		return null;
		},
	 	getLogindate : function(data)
	 	{
	 		log.d("getLogindate");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.logindate;
	 		}
	 		return null;
		},
	 	getUserId : function(data)
	 	{
	 		log.d("getLogindate");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.userId;
	 		}
	 		return null;
		},
	 	getTwitterToken : function(data)
	 	{
	 		log.d("getTwitterToken");
	 		if(data !== null && data !== undefined)
	 		{
		 		log.d("Not null");
		 		return data.params.twitterToken;
	 		}
	 		return null;
		}
 	};
 }
]);