wisdomApp.factory('creativeColorGenerateService', ['log', 'ColorConstants',
 function(log, ColorConstants){
 	log.d("creativeColorGenerateService");

 	var color = ColorConstants;

 	var COLOR_NUM = 19;
 	var ITEM_LENGTH = 4;


 	function getBaseColor(first, second){
 		var parentData = getParentDataset(first);
 		// log.d("parentData: " + parentData);

 		var child = getChildDataset(parentData, second);
 		// log.d("child: " + child);
 		// log.d("result: " + child.value);

 		return child.value;

 	}

 	function getParentDataset(input) {

 		var result = null;

 		switch(input){
 			case 0:
 				result = ColorConstants.UNKNOWN;
 			break;
 			case 1:
 				result = ColorConstants.RED;
 			break;
 			case 2:
 				result = ColorConstants.PINK;
 			break;
 			case 3:
 				result = ColorConstants.PURPLE;
 			break;
 			case 4:
 				result = ColorConstants.DEEP_PURPLE;
 			break;
 			case 5:
 				result = ColorConstants.INDIGO;
 			break;
 			case 6:
 				result = ColorConstants.BLUE;
 			break;
 			case 7:
 				result = ColorConstants.LIGHT_BLUE;
 			break;
 			case 8:
 				result = ColorConstants.CYAN;
 			break;
 			case 9:
 				result = ColorConstants.TEAL;
 			break;
 			case 10:
 				result = ColorConstants.GREEN;
 			break;
 			case 11:
 				result = ColorConstants.LIGHT_GREEN;
 			break;
 			case 12:
 				result = ColorConstants.LIME;
 			break;
 			case 13:
 				result = ColorConstants.YELLO;
 			break;
 			case 14:
 				result = ColorConstants.AMBER;
 			break;
 			case 15:
 				result = ColorConstants.ORANGE;
 			break;
 			case 16:
 				result = ColorConstants.DEEP_ORANGE;
 			break;
 			case 17:
 				result = ColorConstants.BROWN;
 			break;
 			case 18:
 				result = ColorConstants.BLUE_GREY;
 			break;
 			case 19:
 				result = ColorConstants.GREY;
 			break;
 			default:
 				result = ColorConstants.UNKNOWN;
 			break;

 		}

 		return result;
 	}

 	function getChildDataset(childData, index){
 		// log.d("getChildDataset");

 		return childData[index];
 	}


 	return {

 		generateColor : function(input){
 			// log.d("generateColor");

 			var result = null;

 			if(input !== null && input !== undefined) {
 				var first = 0;
 				var second = 0;

 				var str = input.toString(10);

 				switch(str.length){
 					case 0:
						result = getBaseColor(0, 0);
 					break;
 					case 1:
	 					first = (str.charCodeAt(0) % COLOR_NUM);
	 					second = 0;
	 					result = getBaseColor(first, second);
 					break;
 					default:
	 					first = (str.charCodeAt(0) % COLOR_NUM);
	 					second = (str.charCodeAt(1) % ITEM_LENGTH);
	 					result = getBaseColor(first, second);
 					break;
 				}

 			} else {
 				result = getBaseColor(0, 0);
 			}

 			return result;

 		},
 	};

}]);
