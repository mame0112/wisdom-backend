wisdomApp.factory('translationService', ['log', 'Constants', '$translate', '$timeout', '$q',
	function(log, Constants, $translate, $timeout, $q) {
		return {
			getTranslatedStrings: function()
			{
				log.d("getTranslatedStrings");
				// return $q.when("Hello World!");
				 var deferred = $q.defer();
		           $timeout(function(){
		               deferred.resolve("Allo!");
		           },0);
		           return deferred.promise;
			}
		};
	}
]);