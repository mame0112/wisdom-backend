wisdomApp.service('sslConnectionSwitchService', ['$location', 'log', '$window', function($location, log, $window){

    return {
    	forceBasicConnection : function(){
    		log.d("forceBasicConnection: " + $location.absUrl());
			if ($location.protocol() !== 'http') {
		        // $window.location.href = $location.absUrl().replace('https', 'http');
		    }
    	},

    	forceSslConnection : function(){
    		log.d("forceSslConnection: " + $location.absUrl());
			if ($location.protocol() !== 'https') {
				// log.d("target: " + $location.absUrl().replace('http', 'https') + "signup");
		        // $window.location.href = $location.absUrl().replace('http', 'https');
		    }
    	},
    };
}]);