wisdomApp.factory('toasterService', ['toaster', 'Constants', 'log',
 function(toaster, Constants, log){

 	return {

	    howSuccessToasterShort : function(title, text)
	    {
        	toaster.pop('success', this.title, this.text, Constants.TOASTER_SHORT);
		},

    	showWaitToasterShort : function(title, text){
		    toaster.pop('wait', title, text, Constants.TOASTER_SHORT);
 	    },

    	showErrorToasterShort : function(title, text)
    	{
			// toaster.pop('note', "title", "text");
	    	toaster.pop('error', title, text, Constants.TOASTER_SHORT);
    	},

    	showNoteToasterShort : function(title, text)
    	{
	    	toaster.pop('note', title, text, Constants.TOASTER_SHORT);
	    },

    	showSuccessToasterLong : function(title, text)
    	{
     	   toaster.pop('success', title, text, Constants.TOASTER_LONG);
	    },

    	showWaitToasterLong : function(title, text)
    	{
	    	toaster.pop('wait', title, text, Constants.TOASTER_LONG);
	    },

    	showErrorToasterLong : function(title, text)
    	{
	    	toaster.pop('error', title, text, Constants.TOASTER_LONG);
	    },

    	showNoteToasterLong : function(title, text)
    	{
	    	toaster.pop('note', title, text, Constants.TOASTER_LONG);
	    }
 	};
 }]);