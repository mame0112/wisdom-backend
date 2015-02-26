wisdomApp.controller('SidebarController', ['$scope', 'Constants', 'log', function($scope, Constants, log){
 	log.d("SidebarController");
 	log.d(Constants.planetName);

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
