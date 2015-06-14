wisdomApp.factory('createWisdomSharedStateService', ['log', function(log) {

	var messages = {};


	return {

		shareInputMessages : function(messageArray)
		{
			messages = messageArray;
		},

		getSharedMessages : function()
		{
			return messages;
		},

		clearSharedMessages : function()
		{
			messages = null;
		}
	};
}]);