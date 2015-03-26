wisdomApp.controller('toasterController',
 ['log', '$scope', 'toaster',
    function(log, $scope, toaster){

 	log.d("toasterController");

 	$scope.pop = function(){
 		log.d("pop");
        toaster.pop('success', "title", "text");
    };
 }]);