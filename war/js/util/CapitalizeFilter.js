wisdomApp.filter('capitalize', ['log', function(log) {
	return function(input) {
	if (input !== null && input !== undefined)
		return input.substring(0,1).toUpperCase();
	};
}]);
