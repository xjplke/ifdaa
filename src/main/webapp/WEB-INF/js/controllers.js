'use strict';

/* Controllers */

var adfiControllers = angular.module('adfiControllers', ['restangular']);

adfiControllers.config(
		function(RestangularProvider){
			RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
				var extractedData;
				if (operation === "getList") {//getList need response must be list, change  warpper data formate
					extractedData = data.content;
					extractedData.page = data;
				}else{
					extractedData = data;
				}
				return extractedData;
			});
		}
);

adfiControllers.controller('UserController',['$scope','Restangular',
    function($scope,Restangular) {
		$scope.user={};
		$scope.users=[{account:"test"}];
		$scope.page={number:0,size:2,totalElements:0};//init page data for first show!
		
		$scope.orderProp = 'id';
		
		$scope.totalServerItems = 0;
		$scope.pagingOptions = {
			pageSizes:[20,40,80],
			pagesize:20,
			currentPage:1
		}
		
		$scope.gridOptions = {
			data:'users',
			plugins:[new ngGridFlexibleHeightPlugin()],
			enableRowSelection:false,
			showSelectionCheckbox:true,
			pagingOptions:$scope.pagingOptions,
			totalServerItems: 'totalServerItems',
			columnDefs:[{field:"account",displayName:"account"},
			            {field:"username",displayName:"username"},
			            {field:"starttime",displayName:"starttime"},
			            {field:"expire",displayName:"expire"},
			            {field:"",cellTemplate: '<button ng-click="remove(row.entity)">Delete</button>',
			            	displayName:'operator'}]
		};
		
		
		var User = Restangular.all("user");
		
		$scope.show = function(){
			User.getList({page:$scope.page.number,size:$scope.page.size}).then(function(users){
				$scope.page = users.page;
				$scope.users = users;
				$scope.orderProp = 'id';
				$scope.totalServerItems = users.page.totalElements;
			},function(err){
				alert(err);
			})
		}
	}
]);

