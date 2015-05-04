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
	'$state',
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
		$state
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

		//Just in case, we should have this check.
		if(userDataHolder.getUserData() === null || userDataHolder.getUserData() === undefined){
			$state.go('signin');
		}

		// userData =  userDataHolder.getUserData();
		// log.d("userId: " + userData.params.userId);
		// result.createUserId = userData.params.userId;
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
		log.d("result: " + "category: " + result.category + "subCategory: " + result.subCategory +  " tag: " + result.tag + " thumbnail: " + result.thumbnail + " title: " + result.title + " description: " + result.description + "messages: " + result.messages + " createUserId: " + result.create_user_id + " updated time: " + result.updated_date);

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
            data: {data: result},
            // data: angular.toJson($scope.model.fileDescription),
            file: file,
        }).progress(function (evt) {
            $scope.uploadProgress = parseInt(100.0 * evt.loaded / evt.total, 10);
        }).success(function (data) {
        	log.d("success");
        	if(data !== null && data !== undefined){
        		if(data.params.length !== 0){
        			var param = data.params[0];
	        		log.d("wisdomId: " + param.wisdomId);
	        		log.d("point: " + param.point);
		        	toasterService.showSuccessToasterLong("New wisdom", "New wisdom is successfully created. Your point is now " + param.point );
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
