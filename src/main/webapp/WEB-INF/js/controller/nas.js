
function NasListCtrl($scope,$rootScope,$location, Restangular) {
	$scope.searchby = 'type';
	$scope.key = '';
	$scope.loginUser = $rootScope.loginUser;
	
	var searchpath = function(s){
		return s!=''?"/"+s:"";
	}
	
	$scope.page = {number:1,size:10};
	$scope.pageChanged = function(){
		var searchpath = ($scope.searchby!=''&&$scope.key!='')?"/"+$scope.searchby+"/"+$scope.key:null;
		Restangular.all("nas").customGETLIST(searchpath,{'page':$scope.page.number-1,'size':$scope.page.size}).then(function(naslist){
			$scope.naslist = naslist;
			$scope.page = naslist.page;
			$scope.page.number = $scope.page.number + 1;
		},function(err){
			alert(err);
		});
	}
	
	
	$scope.pageChanged();//first call
	
	$scope.destroy = function(nas) {
		nas.remove().then(function() {
			//$location.path('/system/nas');
			$scope.pageChanged();
		});
	};
}

function NasCreateCtrl($scope, $location, Restangular) {
	$scope.save = function() {
		Restangular.all('nas').post($scope.nas).then(function() {
			$location.path('/system/nas');
		});
	}
}


function NasEditCtrl($scope, $location, Restangular, nas) {
  var original = nas;
  $scope.nas = Restangular.copy(original);
  
  $scope.isClean = function() {
    return angular.equals(original, $scope.nas);
  }

  $scope.save = function() {
    $scope.nas.put().then(function() {
      $location.path('/system/nas');
    });
  };
}


