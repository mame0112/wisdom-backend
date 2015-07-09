wisdomApp.controller('wisdomCreateController',
 ['$scope',
   'log',
   'Constants',
   'createWisdomSharedStateService',
   'newWisdomAPIService',
   '$http',
   'userDataHolder',
   'timeService',
   'subCategoryLoaderService',
   'modeService',
   '$upload',
	'$window',
	'$rootScope',
	'toasterService',
	'$state',
	'categoryTranslateService',
	'$timeout',
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
		toasterService,
		$state,
		categoryTranslateService,
		$timeout
    	){
 	log.d("wisdomCreateController");

	$scope.status = {
		isopen1: false,
		isopen2: false
	};

	// $scope.categories = Constants.Category;
	$scope.categories = {};
	$scope.subCategories = null;

	$scope.titleMaxLength = Constants.WISDOM_TITLE_COUNT;
	$scope.descMaxLength = Constants.WISDOM_DESCRIPTION_COUNT;
	$scope.subContentLength = Constants.WISDOM_TITLE_COUNT;

	$scope.tosStatus = false;

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
		"subcategory": subCategory,
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

		//Just in case, we should have this check.
		if(userDataHolder.getUserData() === null || userDataHolder.getUserData() === undefined){
			$state.go('signin');
		}

		categoryTranslateService.getTranslatedCategories().then(function(cs){
			$scope.categories = cs;
		});




		// var cs = categoryTranslateService.getTranslatedCategories().theb({
		// 	function(){

		// 	}
		// });

		// Normal promise version
		// var promise = categoryTranslateService.getTranslatedCategories();
		// promise.then(function(response){
		// 	log.d("success");
		// 	$timeout(function() {
		// 		$scope.categories = response;
		// 		log.d("size: " + $scope.categories);
		// 		log.d("response: " + $scope.categories.translate +  " " + $scope.categories.id + " " + $scope.categories.name);
		// 	}, 0);
		// }, function(reason){
		// 	log.d("fail");
		// }, function(update){
		// 	log.d("notification");
		// });

		// $timeout(function() {
		// 	$scope.categories = categoryTranslateService.getTranslatedCategories();
		// 	log.d("length: " + $scope.categories.length);
		// 	for(var i=0; i<$scope.categories.length; i++){
		// 		var cate = $scope.categories[i];
		// 		log.d("result: " + cate.name + " transate: " + cate.transate);
		// 	}
		// }, 0);

	};


	$scope.addNewWisdom = function(){
		log.d("addNewWisdom");

		userData =  userDataHolder.getUserData();
		log.d("userData: " + userData);
		if(userData !== null && userData !== undefined){
			log.d("userId: " + userData.userId);
			result.create_user_id = userData.userId;
		} else {
			//FIXME This is temporary solution for development.
			result.create_user_id = 2;
		}

		result.updated_date = timeService.getCurrentTime();

		result.messages = createWisdomSharedStateService.getSharedMessages();
		log.d("result: " + "category: " + result.category + "subCategory: " + result.subcategory +  " tag: " + result.tag + " thumbnail: " + result.thumbnail + " title: " + result.title + " description: " + result.description + "messages: " + result.messages + " createUserId: " + result.create_user_id + " updated time: " + result.updated_date);
		log.d("message length: " + result.messages.length);

		$scope.uploadFile();

		// wisdomAPIService.newwisdom({servlet_new_wisdom_param : result});
		// newWisdomAPIService.newwisdom({servlet_new_wisdom_param : result},
		//  function(value, responseHeaders){
		// 	log.d("response received");
		// 	var wisdomId = value.params.param[0].wisdomId;
		// 	// log.d("responseHeaders: " + headers());
		// 	toasterService.showSuccessToasterShort("New wisdom", "Message successfully created!");
	 // 		$state.go('wisdom', {wisdomId : wisdomId});

		//  }, function (httpResponse) {
		//  	//Error case
		// 	log.d("httpResponse: " + httpResponse);
		// 	log.d("status code: " + httpResponse.status);
		// 	toasterService.showErrorToasterShort("New wisdom", "Failed to create wisdom. Please try again");
		// });

	};

	$scope.$watch('category', function(newValue, oldValue) {
		result.category = newValue;
	});

	$scope.$watch('subCategory', function(newValue, oldValue) {
		result.subcategory = newValue;
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
		// $scope.upload($scope.files);
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
	 			// $scope.subCategories = d.data;
	 			categoryTranslateService.getTranslatedSubCategories(d.data).then(function(cs){
					$scope.subCategories = cs;
				});
	 		});
		}
	};


    $scope.model = {};
    $scope.selectedFile = [];
    $scope.uploadProgress = 0;

    $scope.uploadFile = function () {

    	log.d("$scope.model: " + $scope.model);
    	log.d("$scope.model.fileDescription: " + $scope.model.fileDescription);

        var file = $scope.selectedFile[0];
        $scope.upload = $upload.upload({
            // url: '/controller/newwisdom',
            url: Constants.API_V1 + '/newwisdom',
            method: 'POST',
            data: {data: result},
            // data: angular.toJson($scope.model.fileDescription),
            file: file,
        }).progress(function (evt) {
            $scope.uploadProgress = parseInt(100.0 * evt.loaded / evt.total, 10);
        }).success(function (data) {
        	log.d("success");
        	if(data !== null && data !== undefined){
        		if(data.params.length !== 0){

        			//Clear message shared service
        			createWisdomSharedStateService.clearSharedMessages();

        			var param = data.params[0];
	        		log.d("wisdomId: " + param.wisdomId);
	        		log.d("point: " + param.point);
		        	toasterService.showSuccessToasterLong("New wisdom", "New wisdom is successfully created. Your point is now " + param.point );
		        	$state.go('wisdom', {"wisdomId":param.wisdomId});
        		} else {
		        	toasterService.showErrorToasterShort("New wisdom", "Failed to create new wisdom. Please try again");
        		}
        	} else {
            	log.d("illegal response");
	        	toasterService.showErrorToasterShort("New wisdom", "Failed to create new wisdom");
        	}
        });
    };

    $scope.onFileSelect = function ($files) {
        $scope.uploadProgress = 0;
        $scope.selectedFile = $files;
    };

    $scope.tosChanged = function(value)
    {
		$scope.tosStatus = value;
    };

    $scope.shouldShowMessagesNote = function()
    {
    	var messages = createWisdomSharedStateService.getSharedMessages();

    	if(messages !== null){
	    	if(messages === undefined || messages.length === undefined || messages.length === 0)
	    	{
	    		return true;
	    	}
    	}

    	return false;
    };

    $scope.isCreateButtonDisabled = function()
    {
    	//If TOS checkbox is checked
    	if($scope.tosStatus === false){
    		return true;
    	}

    	//If message is inputted more than 1
    	var messages = createWisdomSharedStateService.getSharedMessages();
		if(messages === null || messages === undefined || messages.length === 0){
    		return true;
    	}

    	//If category is enabled
    	if(result.category === null || result.category === undefined){
    		return true;
    	}

    	//If subCategory is enabled
    	if(result.subcategory === null || result.subcategory === undefined){
    		return true;
    	}

    	//If tag is inputted
    	if(result.tag === null || result.tag === undefined){
    		return true;
    	}

    	//If title is inputted
    	if(result.title === null || result.title === undefined){
    		return true;
    	}

    	//If description is inputted
    	if(result.description === null || result.description === undefined){
    		return true;
    	}

    	return false;
    };

	// $scope.uploadFile = function () {
	// 	log.d("uploadFile");
	// 	var file = $scope.files[0];
	// 	$upload.upload({
 //            url: '/controller/newwisdom',
 //            fields: {servlet_resp_id: 1, result: '@servlet_new_wisdom_param'},
 //            // fields: {'username': $scope.username},
 //            file: file
 //        }).progress(function (evt) {
 //            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
 //            console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
 //        }).success(function (data, status, headers, config) {
 //                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
 //        });
 //    };

	// $scope.upload = function (files) {
 //        if (files && files.length) {
	// 		var file = files[0];
	// 		$upload.upload({
 //            url: '/controller/newwisdom',
 //            fields: {servlet_resp_id: 1, result: '@servlet_new_wisdom_param'},
 //                file: file
 //            }).progress(function (evt) {
 //                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
 //                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
 //            }).success(function (data, status, headers, config) {
 //                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
 //            });
 //        }
 //    };

	// $scope.uploadFile = function(){
 //        var file = $scope.myFile;
 //        console.log('file is ' + JSON.stringify(file));
 //        var uploadUrl = "/controller/newwisdom";
 //        fileUpload.uploadFileToUrl(file, uploadUrl);
    // };

}]);

// wisdomApp.directive('fileModel', ['$parse', function ($parse) {
//     return {
//         restrict: 'A',
//         link: function(scope, element, attrs) {
//             var model = $parse(attrs.fileModel);
//             var modelSetter = model.assign;
            
//             element.bind('change', function(){
//                 scope.$apply(function(){
//                     modelSetter(scope, element[0].files[0]);
//                 });
//             });
//         }
//     };
// }]);

// wisdomApp.service('fileUpload', ['$http', 'log', function ($http, log) {

//     this.uploadFileToUrl = function(file, uploadUrl){
//         var fd = new FormData();
//        fd.append('file', file);
// //        fd.append('servlet_resp_id', 1);
// 	    // fd.append('servlet_new_wisdom_param', "test");
//         $http.post(uploadUrl, fd, {
//             transformRequest: angular.identity,
//             headers: {'Content-Type': undefined}
//         })
//         .success(function(){
//         	log.d("success");
//         })
//         .error(function(){
//         	log.d("error");
//         });
//     };
// }]);


wisdomApp.controller('uploadCtrl', [
        '$scope',
        '$upload',
        'log',
        function ($scope, $upload, log) {
            $scope.model = {};
            $scope.selectedFile = [];
            $scope.uploadProgress = 0;

            $scope.uploadFile = function () {

            	log.d("$scope.model: " + $scope.model);
            	log.d("$scope.model.fileDescription: " + $scope.model.fileDescription);

                var file = $scope.selectedFile[0];
                $scope.upload = $upload.upload({
                    url: '/controller/newwisdom',
                    method: 'POST',
                    data: {data: $scope.model.fileDescription},
  					transformRequest: angular.identity,
		            headers: {'Content-Type': undefined},
                    // data: angular.toJson($scope.model.fileDescription),
                    file: file,
                }).progress(function (evt) {
                    $scope.uploadProgress = parseInt(100.0 * evt.loaded / evt.total, 10);
                }).success(function (data) {

                });
            };

            $scope.onFileSelect = function ($files) {
                $scope.uploadProgress = 0;
                $scope.selectedFile = $files;
            };
        }
    ])
    .directive('progressBar', [
        function () {
            return {
                link: function ($scope, el, attrs) {
                    $scope.$watch(attrs.progressBar, function (newValue) {
                        el.css('width', newValue.toString() + '%');
                    });
                }
            };
        }
    ]);
