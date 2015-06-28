wisdomApp.controller('overviewController',
 ['$scope', 
 'log',
 '$state',
function($scope, log, $state){

	$scope.getStartedButtonPressed = function()
	{
		$state.go('signup');
	};
}]);