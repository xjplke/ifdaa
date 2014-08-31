
function RedirRuleListCtrl($scope,$rootScope,$location, Restangular) {
	$scope.searchby = 'ssid';
	$scope.key = '';
	$scope.loginUser = $rootScope.loginUser;
	
	var searchpath = function(s){
		return s!=''?"/"+s:"";
	}
	
	$scope.page = {number:1,size:10};
	$scope.pageChanged = function(){
		var searchpath = ($scope.searchby!=''&&$scope.key!='')?"/"+$scope.searchby+"/"+$scope.key:null;
		Restangular.all("redirrule").customGETLIST(searchpath,{'page':$scope.page.number-1,'size':$scope.page.size}).then(function(rulelist){
			$scope.rulelist = rulelist;
			$scope.page = rulelist.page;
			$scope.page.number = $scope.page.number + 1;
		},function(err){
			alert(err);
		});
	}
	
	
	$scope.pageChanged();//first call
	
	$scope.destroy = function(rule) {
		rule.remove().then(function() {
			//$location.path('/system/nas');
			$scope.pageChanged();
		});
	};
}

function RedirRuleCreateCtrl($scope, $location, Restangular) {
	$scope.save = function() {
		Restangular.all('redirrule').post($scope.rule).then(function() {
			$location.path('/system/redirrule');
		});
	}
}


function RedirRuleEditCtrl($scope, $location, Restangular, rule) {
	var original = rule;
	$scope.rule = Restangular.copy(original);
  
	$scope.isClean = function() {
		return angular.equals(original, $scope.rule);
	}

	$scope.save = function() {
		$scope.rule.put().then(function() {
			$location.path('/system/redirrule');
		});
	};
}


