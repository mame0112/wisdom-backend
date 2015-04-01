wisdomApp.factory('creativeColorGenerateService', ['log', 'ColorConstants',
 function(log, ColorConstants){
 	log.d("creativeColorGenerateService");

 	var color = ColorConstants.RED;

 	return {

 		generateColor : function(input){
 			log.d("generateColor");

 			if(input !== null) {

 			} else {
 				// return color.DEFAULT[0];
 			}

 			// for(var i=0; i<color.RED.length; i++)
 			// {
	 		// 	log.d("result: " + color.RED[i].value);
 			// }
 		},

	 	getBaseColor : function(input){

	 	},

 	};



}]);
