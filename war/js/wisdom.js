var wisdomApp = angular.module('WidsomApp', 
	['ngResource', 
	'ngCookies', 
	'ui.bootstrap', 
	'ui.router', 
	'angularFileUpload', 
	'ngAnimate',
	'toaster'])
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
		state('signin', {
			url: '/signin',
			templateUrl: 'view/signin.html',
			controller: 'SigninController'
		}).
		state('signup', {
			url: '/signup',
			templateUrl: 'view/signup.html',
			controller: 'SignupController'
		}).
		state('privacypolicy', {
			url: '/privacypolicy',
			templateUrl: 'view/privacypolicy.html',
			controller: 'PrivacyController'
		}).
		state('tos', {
			url: '/tos',
			templateUrl: 'view/tos.html',
			controller: 'TosController'
		}).
		state('user', {
			url: '/view',
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		state('user.detail',{
			url: '/view/:userId',
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController'
		}).
		state('wisdom',{
			url: '/detail/:wisdomId',
			templateUrl: 'view/wisdom.html',
			controller: 'wisdomDetailController'
		}).
		state('category',{
			url: '/category/:categoryId/:subCategoryId',
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		state('newwisdom',{
			url: '/wisdom',
			templateUrl: 'view/newwisdom.html',
			controller: 'wisdomCreateController'
		});

}]);
