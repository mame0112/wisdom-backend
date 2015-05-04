wisdomApp.controller('debugController',
 ['$scope', 
 '$http', 
 'log', 
 'modeService', 
 'Constants', 
 'userDataHolder',
 'dataRetriveService', 
 'wisdomAPIService',
 function($scope, $http, log, modeService, Constants, userDataHolder, dataRetriveService, wisdomAPIService){
 	log.d("debugController");

	var data = {
		id: -1,
		title: "test title here.",
		create_user_id: 4,
		updated_date: 123456789,
		description: "test escription",
		version: 1.0,
		category: "Music",
		subcategory: "Jazz",
		tag: "Piano",
		thumbnail: null,
		content:[
		{
			entry: "title text1",
			type: 1
		},
		{
			entry: "title text2",
			type: 2
		},
		{
			entry: "title text3",
			type: 1
		}
		],
	};

	// wisdomAPIService.newwisdom({servlet_new_wisdom_param : data});

 }]);