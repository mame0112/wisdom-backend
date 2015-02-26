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

.directive('breadcrumbDirective', function(){
	return {
		restrict: 'A',
		templateUrl: 'breadcrumb-directive.html'
	};
});
