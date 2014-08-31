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
    		
    		
    		when('/system/redirrule',{
    			templateUrl: 'partials/system/redirrule/list.html',
    			controller: 'RedirRuleListCtrl'
    		}).
    		when('/system/redirrule/edit/:ruleId',{
    			templateUrl: 'partials/system/redirrule/detail.html',
    			controller: 'RedirRuleEditCtrl',
    			resolve: {
  		          	rule: function(Restangular, $route){
  		          		return Restangular.one('redirrule', $route.current.params.ruleId).get();
  		          	}
    			}
    		}).
    		when('/system/redirrule/new',{
    			templateUrl: 'partials/system/redirrule/detail.html',
    			controller: 'RedirRuleCreateCtrl'
    		}).
    		
    		
    		when('/user',{
    			templateUrl: 'partials/user/list.html',
    			controller: 'UserListCtrl'
    		}).
    		when('/user/edit/:userId',{
    			templateUrl: 'partials/user/detail.html',
    			controller: 'UserEditCtrl',
    			resolve:{
    				user:function(Restangular,$route){
    					return Restangular.one('user',$route.current.params.userId).get();
    				}
    			}
    		}).
    		when('/user/new',{
    			templateUrl: 'partials/user/detail.html',
    			controller: 'UserCreateCtrl'
    		}).
    		
    		
    		when('/record',{
    			templateUrl: 'partials/report/list.html',
    			controller: 'RecordListCtrl'
    		}).
    		
    		when('/login',{
    			templateUrl: 'partials/login.html',
    			controller: 'LoginController'
    		}).
    		
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
    	
    	
    	RestangularProvider.setBaseUrl("/rest");
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

adfiApp.run(["$rootScope","$location","Restangular",function($rootScope,$location,Restangular){
	$rootScope.loginUser = {};
	$rootScope.loginUser.logined = false;
	Restangular.one("me").get().then(function(user){
		if(user === undefined){
			$location.path("login");
		}else{
			$rootScope.loginUser = user;
			$rootScope.loginUser.logined = true;
		}
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

adfiApp.filter('booltoicontag',function(){
	return function(b){
		if(b==true){
			return "ok";
		}
		return "remove";
	}
});

adfiApp.filter('timetodatestr',function(){
	return function(time){
		var d = new Date(time);
		//return d.toLocaleDateString();
		return d.format("mm-dd-yyyy");
	}
})


var IPADDR_REGEXP = /^(25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|[1-9])\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|\d)\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|\d)\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]\d|\d)$/;
adfiApp.directive('ipaddres', function() {
  return {
    require: 'ngModel',
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$parsers.unshift(function(viewValue) {
        if (IPADDR_REGEXP.test(viewValue)) {
          // 验证通过
          ctrl.$setValidity('ipaddres', true);
          return viewValue;
        } else {
          // 验证不通过 返回 undefined (不会有模型更新)
          ctrl.$setValidity('ipaddres', false);
          return undefined;
        }
      });
    }
  };
});

adfiApp.directive('ngMatch', ['$parse',function ($parse) {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            var firstPassword = $parse(attrs.ngMatch);
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    // console.info(elem.val() === $(firstPassword).val());
                    ctrl.$setValidity('match', elem.val() === firstPassword(scope));
                });
            });
        }
    }
}]);


