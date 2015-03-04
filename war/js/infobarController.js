wisdomApp.controller('infobarController', ['$scope', '$http', 'log', 'modeService', 'apiService',
 function($scope, $http, log, modeService, apiService){
 	log.d("infobarController");
 	var api = new apiService();
 	api.$popular(function(){
 		log.d("signin finished");
 	});
 	// $http.get('data/mockdata.json').success(function(data){
 	// 	$scope.lists = data;
		// modeService.changeCurrentMode();
		// // log.d("lists: " + $scope.lists);
 	// });

 	$scope.$on('mode_changed', function(event, param){
 		log.d("Mode changed: " + param);
 	});
}]);
