wisdomApp.controller('wisdomCreateController',
 ['$scope',
   'log',
   'Constants',
   'createWisdomSharedStateService',
   'newWisdomAPIService',
   // 'wisdomAPIService',
   '$http',
   'userDataHolder',
   'timeService',
   'subCategoryLoaderService',
   'modeService',
   '$upload',
	'$window',
	'$rootScope',
	'toasterService',
    function(
    	$scope, 
    	log, 
    	Constants, 
    	createWisdomSharedStateService, 
    	newWisdomAPIService, 
    	$http, 
    	userDataHolder, 
    	timeService, 
    	subCategoryLoaderService, 
    	modeService, 
    	$upload,
		$window,
		$rootScope,
		toasterService
    	){
 	log.d("wisdomCreateController");

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	$scope.categories = Constants.Category;
	$scope.subCategories = null;

	$scope.titleMaxLength = Constants.WISDOM_TITLE_COUNT;
	$scope.descMaxLength = Constants.WISDOM_TITLE_COUNT;
	$scope.subContentLength = Constants.WISDOM_TITLE_COUNT;

	var userData = null;

	var category = null;
	var subCategory = null;
	var title = null;
	var tag = null;
	var thumbnail = null;
	var description = null;
	var messages = null;
	var createUserId = -1;
	var updateDate = -1;

	var result = {
		"id": -1,
		"category": category,
		"subCategory": subCategory,
		"title": title,
		"tag": tag,
		"thumbnail": thumbnail,
		"description": description,
		"messages" : messages,
		"create_user_id": createUserId,
		"updated_date": updateDate,
	};

	$scope.initialize = function(){
		//Mode change
		modeService.changeCurrentMode(Constants.STATE.STATE_WISDOM_CREATE_PAGE);

		// userData =  userDataHolder.getUserData();
		// log.d("userId: " + userData.params.userId);
		// result.createUserId = userData.params.userId;
	};


	$scope.addNewWisdom = function(){
		log.d("addNewWisdom");

		userData =  userDataHolder.getUserData();
		log.d("userData: " + userData);
		if(userData !== null && userData !== undefined){
			log.d("userId: " + userData.params.userId);
			result.create_user_id = userData.params.userId;
		} else {
			//FIXME This is temporary solution for development.
			result.create_user_id = 1;
		}

		result.updated_date = timeService.getCurrentTime();

		result.messages = createWisdomSharedStateService.getSharedMessages();
		log.d("result: " + "category: " + result.category + "subCategory: " + result.subCategory +  " tag: " + result.tag + " thumbnail: " + result.thumbnail + " title: " + result.title + " description: " + result.description + "messages: " + result.messages + " createUserId: " + result.create_user_id + " updated time: " + result.updated_date);

		// var newWisdom = new wisdomAPIService();
		// newWisdom.$newwisdom({servlet_resp_id: 1, param: '@servlet_new_wisdom_param'},
		//  function(savedObject, handler){
		// 	log.d("response received");
		// });

		// wisdomAPIService.newwisdom({servlet_new_wisdom_param : result});
		newWisdomAPIService.newwisdom({servlet_new_wisdom_param : result},
		 function(value, responseHeaders){
			log.d("response received");
			// log.d("responseHeaders: " + headers());
			// toasterService.showSuccessToasterShort("New wisdom", "Message successfully created!");
			toasterService.showSuccessToasterShort("New wisdom", "Message successfully created!");
	 		// $window.location.href = '/';

		 }, function (httpResponse) {
		 	//Error case
			log.d("httpResponse: " + httpResponse);
			log.d("status code: " + httpResponse.status);
			toasterService.showErrorToasterShort("New wisdom", "Failed to create wisdom. Please try again");
		});

	};

	$scope.$watch('category', function(newValue, oldValue) {
		result.category = newValue;
	});

	$scope.$watch('subCategory', function(newValue, oldValue) {
		result.subCategory = newValue;
	});

	$scope.$watch('wisdom.title', function(newValue, oldValue) {
		result.title = newValue;
	});

	$scope.$watch('wisdom.description', function(newValue, oldValue) {
		result.description = newValue;
	});

	$scope.$watch('wisdom.tag', function(newValue, oldValue) {
		result.tag = newValue;
	});

	$scope.$watch('thumbnail', function(newValue, oldValue) {
		log.d("thumbnail loaded");
		result.thumbnail = newValue;
	});

	$scope.$watch('files', function (newValue, oldValue) {
		log.d("file loaded");
		$scope.upload($scope.files);
		// if(newValue !== null && newValue !== undefined){
			// result.thumbnail = newValue;
			// $scope.files= newValue;
		// }

        // $scope.upload($scope.files);
    });

	$scope.getSubcategoryItems = function(category)
	{
		log.d("getSubcategoryItems: " + category);
		if(category !== null && category !== undefined) {
	 		subCategoryLoaderService.load(category).then(function(d){
	 			log.d("d: " + d.data);
	 			$scope.subCategories = d.data;
	 		});

			// var jsonFile = category.toLowerCase();

			// $http({
			// 	method: 'GET', 
			// 	url: '/data/' + jsonFile + '.json'
			// }).
	  // 		success(function(data, status, headers, config) {
	  // 			log.d("success: " + data);
	  // 			$scope.subCategoryTitles = data;
	  // 		}).
			// error(function(data, status, headers, config) {
			// 	console.log('error');
			// });
		}
	};

	$scope.uploadFile = function () {
		log.d("uploadFile");
		var file = $scope.files[0];
		$upload.upload({
            url: '/controller/newwisdom',
            fields: {servlet_resp_id: 1, result: '@servlet_new_wisdom_param'},
            // fields: {'username': $scope.username},
            file: file
        }).progress(function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
        }).success(function (data, status, headers, config) {
                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
        });
    };

	$scope.upload = function (files) {
        if (files && files.length) {
			var file = files[0];
			$upload.upload({
            url: '/controller/newwisdom',
            fields: {servlet_resp_id: 1, result: '@servlet_new_wisdom_param'},
                file: file
            }).progress(function (evt) {
                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
            }).success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            });
        }
    };

}]);
