wisdomApp.service('modeService', ['$rootScope', 'log', 'Constants', function($rootScope, log, Constants){

	var currentMode = Constants.STATE.STATE_HOME_NO_LOGIN;

    return {
        getCurrentMode : function(){
            return currentMode;
        },

        changeCurrentMode : function(param){
        	// log.d("changeCurrentMode: " + param);
            if(param !== null && param !== undefined){
                currentMode = param;
                $rootScope.$broadcast(Constants.MODE_CHNAGE, param);
            }
        }
    };
}]);