wisdomApp.service('userDataHolder', ['$cookieStore', 'log', function($cookieStore, log){

    return {
        setUserData : function(data){
        	log.d("setUserData");
            if(data !== null){
                $cookieStore.put("userData", data);
            }
    	},

        getUserData : function(){
        	log.d("getUserData");
            // var nowDate = new Date();
            //FIXME
            // var endDate = new Date("2015/09/22 22:00:00");

            // if(nowDate > endDate){
                // $cookieStore.remove("userData");
            // }
            // $scope.result = $cookieStore.get("userData");
            // return $scope.result;
            return $cookieStore.get("userData");
        }
    };
}]);