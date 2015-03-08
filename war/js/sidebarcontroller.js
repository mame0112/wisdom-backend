wisdomApp.controller('SidebarController', ['$scope', 'Constants', 'log', function($scope, Constants, log){

 	$scope.Category = Constants.Category;

 	$scope.initialize = function() {
		log.d("initialize");
		setupItems();
 	};

 	$scope.isVisible = function() {
		log.d("isVisible");
 	};

 	var setupItems = function(){
 		log.d("setupItems");
 	};

}]);
