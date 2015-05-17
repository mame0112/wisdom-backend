wisdomApp.factory('Page', ['$rootScope', 'log', function($rootScope, log) {
	// $rootscope.pageTitle = 'default';
	return {
		title: function()
		{
			log.d("title: " + $rootScope.pageTitle);
			return $rootScope.pageTitle;
		},

		setTitle: function(newTitle)
		{
			log.d("setTitle: " + newTitle);
			$rootScope.pageTitle = newTitle;
		}
	};
}]);