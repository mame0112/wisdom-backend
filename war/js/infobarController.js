wisdomApp.controller('infobarController', ['$scope', '$http', 'log', 'modeService', 'apiService', 'highlightAPIService',
 function($scope, $http, log, modeService, apiService, highlightAPIService){
 	log.d("infobarController");

 	$scope.highlight = null;

 	var offset = 0;

 	var param = {
 		"offset" : offset
 	};

 	highlightAPIService.highlight({servlet_params : param}, function(data){
 		// log.d("response data: " + data); 		
	 	$scope.highlights = data.params;
	 	$scope.data = data;
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
