'use strict';

/* App Module */

var adfiApp = angular.module('adfiApp', [
  'ngRoute',
  'ngGrid',
  'adfiControllers'
]);

adfiApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/', {
        //templateUrl: 'partials/dashboard.html',
    	templateUrl: 'partials/user/list.html',
        controller: 'UserController'
/*      }).
      when('/system/nas',{
    	templateUrl: 'partials/system/nas.html',
    	controller: 'nasController'
      }).
      when('/user/list',{
    	templateUrl: 'partials/user/list.html',
    	controller: 'userController'
      }).
      when('/user/add',{
      	templateUrl: 'partials/user/create.html',
      	controller: 'userController'*/
      }).
      otherwise({
        redirectTo: '/'
      });
  }]);
