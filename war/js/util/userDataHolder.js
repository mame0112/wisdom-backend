wisdomApp.service('userDataHolder',
 ['$cookieStore', 'log', 'Constants', 
  function($cookieStore, log, Constants){

    return {
        setUserData : function(data){
        	log.d("setUserData");
            if(data !== null){
                log.d("SET");
                $cookieStore.put("userData", data);
            }
    	},

        getUserData : function(){
        	// log.d("getUserData");
            // var nowDate = new Date();
            //FIXME
            // var endDate = new Date("2015/09/22 22:00:00");

            // if(nowDate > endDate){
                // $cookieStore.remove("userData");
            // }
            // $scope.result = $cookieStore.get("userData");
            // return $scope.result;
            var data  = $cookieStore.get("userData");
            // log.d("data:" + data);

            return data;
        },

        getUserName : function(){
            if($cookieStore.get("userData") !== null && $cookieStore.get("userData") !== undefined)
            {
                return $cookieStore.get("userData").username;
            }
        },

        getUserId : function(){
            if($cookieStore.get("userData") !== null && $cookieStore.get("userData") !== undefined) {
                if($cookieStore.get("userData").userId !== null && $cookieStore.get("userData").userId !== undefined){
                    return $cookieStore.get("userData").userId;
                }
            }

            return Constants.NO_USER;
        },

        removeUserData : function()
        {
            $cookieStore.remove("userData");
        }
    };
}]);