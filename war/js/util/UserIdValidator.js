wisdomApp.factory('UserIdValidator', ['log', 'Constants', 'userDataHolder',
 function(log, Constants, userDataHolder){
 	return {
 		validateUserId : function(userId){
	 		var result = Constants.VALIDATE_RESULT.VALID_ID;

	 		result = isUserIdIsNotNull(userId);

	 		//If user id is neither null nor undefined
	 		if(result === Constants.VALIDATE_RESULT.VALID_ID){
		 		//Check if user id is not NO_USER
		 		result = IsUserIdValid(userId);

		 		//If user id is not NO_USER, go to next check
		 		if(result === Constants.VALIDATE_RESULT.VALID_ID){
			 		result = IsUserIdOwnedByUser(userId);
		 		}
	 		}

	 		return result;
 		}

 	};

 	function isUserIdIsNotNull(userId){
 		if (userId === null || userId === undefined){
 			return Constants.VALIDATE_RESULT.UNDEFINED;
 		} else {
 			return Constants.VALIDATE_RESULT.VALID_ID;
 		}
 	}

 	function IsUserIdValid(userId){

 		if(userId === Constants.NO_USER){
 			return Constants.VALIDATE_RESULT.ILLEGAL_ID;
 		} else {
 			return Constants.VALIDATE_RESULT.VALID_ID;
 		}

 	}

  	function IsUserIdOwnedByUser(userId){

  		//check if given user id is same with user id which is stored on userDataHolder
 		if(Number(userId) !== userDataHolder.getUserId()){
 			return Constants.VALIDATE_RESULT.ID_NOT_OWNED_BY_USER;
 		} else {
 			return Constants.VALIDATE_RESULT.VALID_ID;
 		}

 	}

 }]);