wisdomApp.controller('infobarController', ['$scope', '$http', 'log', 'modeService', function($scope, $http, log, modeService){
 	log.d("infobarController");
// 	$http.get('data/mockdata.json').success(function(data){
// 		$scope.lists = data;
//		modeService.changeCurrentMode();
//		// log.d("lists: " + $scope.lists);
// 	});
 	$http.get('/controller/signin').success(function(data){
 		$scope.lists = data;
		modeService.changeCurrentMode();
		log.d("lists: " + $scope.lists);
 	});

 	$scope.$on('mode_changed', function(event, param){
 		log.d("Mode changed: " + param);
 	});
}]);
