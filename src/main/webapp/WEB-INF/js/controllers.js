'use strict';

/* Controllers */

var adfiControllers = angular.module('adfiControllers', ['restangular','ui.bootstrap']);

adfiControllers.config(
		function(RestangularProvider){
			RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
				var extractedData;
				if (operation === "getList") {//getList need response must be list, change  warpper data formate
					//Restangular 的getList要求必须返回list对象，而实际接口返回的是page对象，所以这里处理了一下，把page里面的list作为最后的返回值，而将page对象挂在list对象下面了。
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



adfiControllers.controller('NasController',['$scope','$modal', '$log','Restangular',
    function( $scope, $modal, $log, Restangular) {
		$scope.nasList = [];
		$scope.orderProp = 'id';
		$scope.totalServerItems = 0;
		$scope.pagingOptions = {
			pageSizes:[20,40,80],
			pagesize:20,
			currentPage:1
		}
		$scope.page={number:0,size:$scope.pagingOptions.pagesize,totalElements:0};//init page data for first show!
		
		$scope.gridOptions = {
				data:'nasList',
				plugins:[new ngGridFlexibleHeightPlugin()],
				enableRowSelection:false,
				showSelectionCheckbox:true,
				pagingOptions:$scope.pagingOptions,
				totalServerItems: 'totalServerItems',
				columnDefs:[{field:"nasname",displayName:"Nas"},
				            {field:"shortname",displayName:"Short Name"},
				            {field:"server",displayName:"Nas IP"},
				            {field:"ports",displayName:"Port"},
				            {field:"secret",displayName:"Secret"},
				            {field:"description",displayName:"Description"},
				            {field:"type",displayName:"Type"},
				            {field:"",cellTemplate: '<button ng-click="editNas(row.entity.id)">edit</button><button ng-click="remove(row.entity.id)">Delete</button>',
				            			displayName:'Operator'}]
				
		}
	
		var Nas = Restangular.all("nas");
		
		var ModalNasEditCtrl = function ($scope, $modalInstance, nas) {
			$scope.nas = nas;

			$scope.ok = function () {
				$modalInstance.close($scope.nas);/*will call result.then first func*/
			};

			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');/*will call result.then second func*/
			};
		}
			
		var open = function (size) {
		    var modalInstance = $modal.open({
		    	templateUrl: 'partials/system/nasEdit.html',
		    	controller: ModalNasEditCtrl,
		    	size: size,
		    	resolve: {
		    		nas: function () {
		    			return $scope.nas;
		    		}
		    	}
		    });

		    modalInstance.result.then(/*ok process*/function (nas) {
		    		//$scope.selected = selectedItem;
		    		nas.save();
		    		$scope.showList();
		    	},/*cancel*/function () {
		    		$log.info('Modal dismissed at: ' + new Date());
		    	}
		    );
		}
		
		$scope.showList = function(){
			//$scope.nasList = [{nasname:"nas1xx",shortname:"nas1",description:"nas1",ports:"123",secret:"123qwe",server:"127.0.0.1",type:"type1"},
			//                  {nasname:"nas2yy",shortname:"nas2",description:"nas2",ports:"123",secret:"123qwe",server:"127.0.0.2",type:"type2"}];
			Nas.getList({page:$scope.page.number,size:$scope.page.size}).then(function(nasList){
				$scope.page = nasList.page;
				$scope.nasList = nasList;
				$scope.orderProp = 'id';
				$scope.totalServerItems = nasList.page.totalElements;
			},function(){
				alert(err);
			}); 
			$scope.page = $scope.nasList.page;
			
		}
		
		$scope.createNas = function() {
			$scope.nas = Nas.one();//{};
			open();
		}
		
		$scope.editNas = function(id){
			//alert(id);
			$scope.nas = Restangular.one("nas",id).get();
			//$scope.nas = $scope.nasList[0];
			open();
		}
		
		$scope.deleteNas = function(){
			
		}
		
		
	}
]);
