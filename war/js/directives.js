wisdomApp.directive('headerDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'header-directive.html'
	};
})

.directive('footerDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'footer-directive.html'
	};
})

.directive('titleDirective', ['$rootScope', '$timeout', 
	function($rootScope, $timeout){
	return {
		restrict: 'AEC',
		link: function(scope, element) {

			var listener = function(event, toState) {

				console.log("set new title: " + $rootScope.pageTitle);

				var serviceName = 'chienowa[チエノワ]';
				var title = null;

				if (toState.data && toState.data.pageTitle) {
					title = toState.data.pageTitle + ' | ' + serviceName;
				} else {
					title = serviceName;
				}

				$timeout(function() {
					element.text(title);
				}, 0, false);
			};

			$rootScope.$on('$stateChangeSuccess', listener);
		}
	};
}])

.directive('heroshotDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'heroshot-directive.html'
	};
})

.directive('sidebarDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'sidebar-directive.html'
	};
})

.directive('infobarDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'infobar-directive.html'
	};
})

.directive('pickupDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'pickup-directive.html'
	};
})

.directive('rankingDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'ranking-directive.html'
	};
})

.directive('breadcrumbDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'breadcrumb-directive.html'
	};
})

.directive('toasterDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'toaster-directive.html'
	};
})

.directive('messageControlDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'message-control-directive.html'
	};
})

//Directive for category view. This is for content part.
.directive('categoryContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/category-content-directive.html'
	};
})

// Directives for Wisdom page 
.directive('wisdomContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/wisdom-content-directive.html'
	};
})

.directive('wisdomInfoDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/wisdom-info-directive.html'
	};
})

// Directives for new wisdom creation page
.directive('newwisdomContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/newwisdom-content-directive.html'
	};
})

.directive('newwisdomInfoDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/newwisdom-info-directive.html'
	};
})

.directive('wisdomModifyContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/wisdom-modify-content-directive.html'
	};
})

.directive('wisdomModifyNoteDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/wisdom-modify-note-directive.html'
	};
})

.directive('contactContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/contact-content-directive.html'
	};
})

.directive('contactNoteDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/contact-note-directive.html'
	};
})

.directive('signupContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/signup-content-directive.html'
	};
})

.directive('signupNoteDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/signup-note-directive.html'
	};
})

.directive('signinContentDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/signin-content-directive.html'
	};
})

.directive('signinNoteDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'directive/signin-note-directive.html'
	};
})

.directive('inputtedMessageController', function(){
	return {
		restrict: 'A',
		controller: 'inputtedMessageController',
		templateUrl: 'directive/inputted-message-directive.html'
	};
})

// Directive for debug
.directive('debugDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'debug-directive.html'
	};
});
