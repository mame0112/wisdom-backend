var wisdomApp = angular.module('WidsomApp', ['ngRoute','ngResource'])
.controller('WidsomController', ['$scope', 'Constants', 'log', function($scope, Constants, log){
 	log.d("WidsomController");
 	log.d(Constants.planetName);
//	console.log("planet: " + Constants.planetName);
	$scope.message =  'Test message from controller';
}])

.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: 'view/toppage.html',
			controller: 'WidsomController'
		}).
		when('/signin', {
			templateUrl: 'view/signin.html',
			controller: 'SigninController'
		}).
		when('/signup', {
			templateUrl: 'view/signup.html',
			controller: 'SignupController'
		}).
		when('/view', {
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		when('/view/:userId',{
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		when('/category/:categoryId',{
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		otherwise({
			redirectTo: '/'
		});
}]);
