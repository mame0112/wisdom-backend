var wisdomApp = angular.module('WidsomApp', ['ngResource', 'ngCookies', 'ui.bootstrap', 'ui.router'])
.controller('WidsomController', ['$scope', 'Constants', 'log', function($scope, Constants, log){
}])

.config(['$stateProvider', '$urlRouterProvider',
	function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise('/');
		$stateProvider.
		state('/', {
			url: '/',
			templateUrl: 'view/toppage.html',
			controller: 'WidsomController'
		}).
		state('/signin', {
			url: '/signin',
			templateUrl: 'view/signin.html',
			controller: 'SigninController'
		}).
		state('/signup', {
			url: '/signup',
			templateUrl: 'view/signup.html',
			controller: 'SignupController'
		}).
		state('/view', {
			url: '/view',
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		state('/view/:userId',{
			url: '/view/:userId',
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		state('/detail/:wisdomId',{
			url: '/detail/:wisdomId',
			templateUrl: 'view/wisdom.html',
			controller: 'wisdomDetailController'
		}).
		state('/category/:categoryId/:subCategoryId',{
			url: '/category/:categoryId/:subCategoryId',
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		state('/wisdom',{
			url: '/wisdom',
			templateUrl: 'view/newwisdom.html',
			controller: 'wisdomCreateController'
		});

}]);
