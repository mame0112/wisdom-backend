var wisdomApp = angular.module('WidsomApp', ['ngRoute','ngResource', 'ngCookies', 'ui.bootstrap'])
.controller('WidsomController', ['$scope', 'Constants', 'log', function($scope, Constants, log){
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
		when('/category/:categoryId/:subCategoryId',{
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		when('/wisdom',{
			templateUrl: 'view/newwisdom.html',
			controller: 'wisdomCreateController'
		});
		// otherwise({
		// 	redirectTo: '/'
		// });

}]);
