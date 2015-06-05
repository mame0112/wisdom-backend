wisdomApp.factory('facebookSignupService', ['log', 'Constants', '$translate', '$timeout', '$q', '$facebook','facebookSignupAPIService',
	function(log, Constants, $translate, $timeout, $q, $facebook, facebookSignupAPIService) {
	return {
		signUpFacebook: function()
		{
			log.d("signUpFacebook");
			return $facebook.login().then( tryFacebookSignup );


		// $facebook.api("/me").then( 
		// function(response) {
		// 	log.d("refresh response: " + response);
		// 	log.d("Welcome: " + response.id);
		// 	log.d("Welcome: " + response.name);

		// 	var pictureUrl = "http://graph.facebook.com/"+ response.id + "/picture?type=large";

		// 	log.d("pictureUrl: " + pictureUrl);

		// var servlet_facebook_name;
		// var servlet_thumbnail_url;

		// var params = {
		// 	servlet_facebook_name:response.name,
		// 	servlet_thumbnail_url:pictureUrl
		// };

		// log.d("params.servlet_facebook_name: " + params.servlet_facebook_name);
		// log.d("params.servlet_thumbnail_url: " + params.servlet_thumbnail_url);

		// facebookSignupAPIService.facebookSignup({servlet_params : params}, function(response){
		// 	if(response !== null && response !== undefined){
		// 		if(response.params !== null && response.params !== undefined){
		// 			log.d("successfully facebook sign in and store data");
		// 			toasterService.showSuccessToasterShort("Signup", "Account successfully created!");

		// 			var userData = {
		// 				"userId":response.params[0].userId,
		// 				"username":params.servlet_facebook_name,
		// 				"thumbnailUrl":params.servlet_thumbnail_url
		// 			};

		// 			//TODO need to support user thumbnail
		// 			userDataHolder.setUserData(userData);

		// 			$state.go('/');
		// 		} else {
		// 			log.d("fail");
		// 			toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		// 		}
		// 	} else {
		// 		log.d("fail");
		// 		toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		// 	}
		// });
		}
	};

	function tryFacebookSignup()
	{
		log.d("tryFacebookSignup");

		$facebook.api("/me").then( 
		function(response) {
			log.d("refresh response: " + response);
			log.d("Welcome: " + response.id);
			log.d("Welcome: " + response.name);

			var pictureUrl = "http://graph.facebook.com/"+ response.id + "/picture?type=large";

			return getFacebookUserData(response.name, pictureUrl);

		});

	}

	function getFacebookUserData(name, pictureUrl)
	{
		var servlet_facebook_name;
		var servlet_thumbnail_url;

		var params = {
			servlet_facebook_name: name,
			servlet_thumbnail_url:pictureUrl
		};

		log.d("params.servlet_facebook_name: " + params.servlet_facebook_name);
		log.d("params.servlet_thumbnail_url: " + params.servlet_thumbnail_url);

		// facebookSignupAPIService.facebookSignup({servlet_params : params}).$promise.then(function(response){
		// 	return 	response;
		// });
		return facebookSignupAPIService.facebookSignup({servlet_params : params});

		// facebookSignupAPIService.facebookSignup({servlet_params : params}, function(response){
		// 	return response;
		// });

		// facebookSignupAPIService.facebookSignup({servlet_params : params}, function(response){
		// 	if(response !== null && response !== undefined){
		// 		if(response.params !== null && response.params !== undefined){
		// 			log.d("successfully facebook sign in and store data");
		// 			toasterService.showSuccessToasterShort("Signup", "Account successfully created!");

		// 			var userData = {
		// 				"userId":response.params[0].userId,
		// 				"username":params.servlet_facebook_name,
		// 				"thumbnailUrl":params.servlet_thumbnail_url
		// 			};

		// 			//TODO need to support user thumbnail
		// 			userDataHolder.setUserData(userData);

		// 			$state.go('/');
		// 		} else {
		// 			log.d("fail");
		// 			toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		// 		}
		// 	} else {
		// 		log.d("fail");
		// 		toasterService.showErrorToasterLong("Signup", "Something went wrong. Please try again later");
		// 	}
		// });
	}


}]);
