'use strict';
/*model*/


var models = angular.module('adfimodels',['ngResource']);

models.factory('User',function($resource){
	return $resource("/user/:userId",{userId:'@id'});
});
