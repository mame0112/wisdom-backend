wisdomApp.controller('toasterController',
 ['log', '$scope', 'toaster', 'Constants', '$timeout', 'timeService',
    function(log, $scope, toaster, Constants, $timeout, timeService){

 	log.d("toasterController");

    var isUpdated = false;
    var promise = null;
    var lastTime = 0;

    $scope.$on('show_toast', function(event, param){
        log.d("show toast received");
        // if(isUpdated === false)
        // {
        //     isUpdated = true;
        //     log.d("Changed to true");
        //     $scope.showNoteToasterShort("test title", "test text");
        //     promise = $timeout(checkTime, 5000);
        // }
        var now = timeService.getCurrentTime();
        if(now - lastTime >= 1000)
        {
            // isUpdated = true;
            log.d("Changed to true");
            $scope.showNoteToasterShort("test title", "test text");
            lastTime = now;
            // promise = $timeout(checkTime, 5000);
        }


        // $scope.showSuccessToasterShort("test title", "test text");
    });

    $scope.showSuccessToasterShort = function(title, text){
        toaster.pop('success', title, text, Constants.TOASTER_SHORT);
    };

    $scope.showWaitToasterShort = function(title, text){
	    toaster.pop('wait', title, text, Constants.TOASTER_SHORT);
    };

    $scope.showErrorToasterShort = function(title, text){
	    toaster.pop('error', title, text, Constants.TOASTER_SHORT);
    };

    $scope.showNoteToasterShort = function(title, text){
	    toaster.pop('note', title, text, Constants.TOASTER_SHORT);
    };

    $scope.showSuccessToasterLong = function(title, text){
        toaster.pop('success', title, text, Constants.TOASTER_LONG);
    };

    $scope.showWaitToasterLong = function(title, text){
	    toaster.pop('wait', title, text, Constants.TOASTER_LONG);
    };

    $scope.showErrorToasterLong = function(title, text){
	    toaster.pop('error', title, text, Constants.TOASTER_LONG);
    };

    $scope.showNoteToasterLong = function(title, text){
	    toaster.pop('note', title, text, Constants.TOASTER_LONG);
    };


    // function checkTime()
    // {
    //     isUpdated = false;
    //     log.d("checkTime");
    //     $timeout.cancel();
    // }

    // promise = function() {
    //     $timeout(function(){
    //         log.d("isUpdated is changed to false");
    //         isUpdated = false;
    //     }, 1000);
    // };

    // showToaster = function(title, text)
    // {
    //     log.d("showToaster");
    //     if(isUpdated === false){
    //         log.d("isUpdated is false and promise is null");
    //         $scope.showNoteToasterLong(title, text);
    //         isUpdated = true;
    //     } else {
    //         log.d("isUpdated is true");
    //     }
    // };

    $scope.$on("$destroy", function(event){
        $timeout.cancel(promise);
    });

 }]);

