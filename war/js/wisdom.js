var wisdomApp = angular.module('WidsomApp', 
	['ngResource', 
	'ngCookies', 
	'ui.bootstrap', 
	'ui.router', 
	'angularFileUpload', 
	'ngAnimate',
	'toaster',
	'angulartics',
	'angulartics.google.analytics',
	])
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
		}).
		state('signup', {
			url: '/signup',
			templateUrl: 'view/signup.html',
			controller: 'SignupController'
		}).
		state('overview', {
			url: '/overview',
			templateUrl: 'view/overview.html'
		}).
		state('privacypolicy', {
			url: '/privacypolicy',
			templateUrl: 'view/privacypolicy.html'
		}).
		state('tos', {
			url: '/tos',
			templateUrl: 'view/tos.html',
		}).
		state('contact', {
			url: '/contact',
			templateUrl: 'view/contact.html',
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
		}).
		state('category',{
			url: '/category/:categoryId/:subCategoryId',
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		state('newwisdom',{
			url: '/wisdom',
			templateUrl: 'view/newwisdom.html',
		}).
		state('search',{
			url: '/search:result',
			templateUrl: 'view/search.html',
			controller: 'searchResultController'
		});

}]);
