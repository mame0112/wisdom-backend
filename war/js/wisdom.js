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
	'ngProgress',
	'pascalprecht.translate',
	'ngCordova'
	])
.controller('WidsomController', ['$scope', 'Constants', 'log', function($scope, Constants, log){
	
}])

.config(['$stateProvider', '$urlRouterProvider', '$translateProvider',
	function($stateProvider, $urlRouterProvider, $translateProvider) {

		$translateProvider.useStaticFilesLoader({
	        prefix : '../lang/lang_',
	        suffix : '.json'
	    });
	    // $translateProvider.preferredLanguage(findLanguage());
	    // $translateProvider.determinePreferredLanguage();

		$translateProvider.preferredLanguage('ja');
		$translateProvider.fallbackLanguage('en');
		$translateProvider.useMissingTranslationHandlerLog();
		$translateProvider.useLocalStorage();
		$translateProvider.useSanitizeValueStrategy('escaped');

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
			data : { pageTitle: 'Sign in' },
			isSslRequired: true
		}).
		state('signup', {
			url: '/signup',
			templateUrl: 'view/signup.html',
			controller: 'SignupController',
			data : { pageTitle: 'Sign up' },
			isSslRequired: true
		}).
		state('overview', {
			url: '/overview',
			templateUrl: 'view/overview.html',
			data : { pageTitle: 'Overview' }
		}).
		state('privacypolicy', {
			url: '/privacypolicy',
			templateUrl: 'view/privacypolicy.html',
			data : { pageTitle: 'Privacy policy' }
		}).
		state('tos', {
			url: '/tos',
			templateUrl: 'view/tos.html',
			data : { pageTitle: 'Terms of service' }
		}).
		state('contact', {
			url: '/contact',
			templateUrl: 'view/contact.html',
			data : { pageTitle: 'Contact' },
			isSslRequired: true
		}).
		state('user', {
			url: '/view',
			templateUrl: 'view/userpage.html',
			data : { pageTitle: 'User page' }
		}).
		state('user.detail',{
			url: '/view/:userId',
			templateUrl: 'view/userpage.html',
			controller: 'UserDataController',
			data : { pageTitle: 'User page' }
		}).
		state('wisdom',{
			url: '/detail/:wisdomId',
			templateUrl: 'view/wisdom.html',
			data : { pageTitle: 'Wisdom' },
		}).
		state('category',{
			url: '/category/:categoryId/:subCategoryId',
			templateUrl: 'view/category.html',
			controller: 'categoryController'
		}).
		state('newwisdom',{
			url: '/wisdom',
			templateUrl: 'view/newwisdom.html',
			data : { pageTitle: 'New wisdom' }
		}).
		state('modifywisdom',{
			url: '/modify/:currentWisdom',
			templateUrl: 'view/modifywisdom.html',
			data : { pageTitle: 'Modify wisdom' }
		}).
		state('search',{
			url: '/search:result',
			templateUrl: 'view/search.html',
			controller: 'searchResultController',
			data : { pageTitle: 'Search wisdom' }
		});

}])

.run(['$rootScope', '$state', 'sslConnectionSwitchService', function ($rootScope, $state, sslConnectionSwitchService) {
    $rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams){
		sslConnectionSwitchService.forceSslConnection();
   //      if (toState.isSslRequired) {
			// sslConnectionSwitchService.forceSslConnection();
   //      } else {
			// sslConnectionSwitchService.forceBasicConnection();			
   //      }
        // e.preventDefault();
            // $state.go('signin');
    });

    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
    	console.log("BBB");
        $rootScope.title = current.$$route.title;
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
    	console.log("CCC");
        // $rootScope.title = toParams.$$route.title;
    });
}]);


function findLanguage() {
    try {
        return (navigator.browserLanguage || navigator.language || navigator.userLanguage).substr(0, 2);
    } catch (e) {
        return "en";
    }
}
