'use strict';

/* App Module */

var adfiApp = angular.module('adfiApp', [
  'ngRoute',
  'ngGrid',
  'restangular',
  //'ui.router',
  'ui.bootstrap'
  //'adfiControllers'
]);

adfiApp.config(['$routeProvider','$httpProvider','RestangularProvider',
    function($routeProvider,$httpProvider,RestangularProvider) {
    	$routeProvider.
    		when('/', {
    			//templateUrl: 'partials/dashboard.html',
    			templateUrl: 'partials/system/nas/list.html',
    			controller: 'NasListCtrl'
    		}).
    		when('/system/nas',{
    			templateUrl: 'partials/system/nas/list.html',
    			controller: 'NasListCtrl'
    		}).
    		when('/system/nas/edit/:nasId',{
    			templateUrl: 'partials/system/nas/detail.html',
    			controller: 'NasEditCtrl',
    			resolve: {
    		          nas: function(Restangular, $route){
    		            return Restangular.one('nas', $route.current.params.nasId).get();
    		          }
    		    }
    		}).
    		when('/system/nas/new',{
    			templateUrl: 'partials/system/nas/detail.html',
    			controller: 'NasCreateCtrl'
    		}).
    		//when('/user/list',{
    		//	templateUrl: 'partials/user/list.html',
    		//	controller: 'UserController'
    		//}).
    		//when('/user/add',{
    		//	templateUrl: 'partials/user/create.html',
    		//	controller: 'UserController'
    		//}).
    		otherwise({
    			redirectTo: '/'
    		});
    	
    	$httpProvider.interceptors.push(function($q){
    		return {
    			'responseError':function(rejection){
    				alert("status:"+rejection.data.status+"\nerror:"+rejection.data.error+
    						"\nmessage:"+rejection.data.message+"\nexception:"+rejection.data.exception);
    				return $q.reject(rejection);
    			}
    		};
    	});
    	
    	//config RestangularProvider
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
  	}]);


adfiApp.filter('shortcut',function(){
	return function(s,len){
		if(typeof(s)=='undefine'){
			return '';
		}
		if(typeof(s)!='string'){
			return s;
		}
		var length = len | 20;
		if(s.length > length){
			return s.substr(0,length-4)+'...';
		}
		return s;
	};
});
