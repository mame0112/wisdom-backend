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


  $scope.groups = [
    {
      title: 'Dynamic Group Header - 1',
      content: 'Dynamic Group Body - 1'
    },
    {
      title: 'Dynamic Group Header - 2',
      content: 'Dynamic Group Body - 2'
    }
  ];

  $scope.items = ['Item 1', 'Item 2', 'Item 3'];

  $scope.addItem = function() {
    var newItemNo = $scope.items.length + 1;
    $scope.items.push('Item ' + newItemNo);
  };

  $scope.status = {
    isFirstOpen: true,
    isFirstDisabled: false
  };


}]);
