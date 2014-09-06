
function UserListCtrl($scope,$rootScope,$location, Restangular) {
	$scope.searchby = 'username';
	$scope.key = '';
	
	$scope.loginUser = $rootScope.loginUser;
	
	var searchpath = function(s){
		return s!=''?"/"+s:"";
	}
	
	$scope.page = {number:1,size:10};
	$scope.pageChanged = function(){
		var searchpath = ($scope.searchby!=''&&$scope.key!='')?"/"+$scope.searchby+"/"+$scope.key:null;
		Restangular.all("user").customGETLIST(searchpath,{'page':$scope.page.number-1,'size':$scope.page.size}).then(function(userlist){
			$scope.userlist = userlist;
			$scope.page = userlist.page;
			$scope.page.number = $scope.page.number + 1;
		});
	}
	
	$scope.isManager=function(user){
		return user.username === "admin" || user.username === "viewer";
	}
	
	
	$scope.pageChanged();//first call
	
	$scope.destroy = function(user) {
		user.remove().then(function() {
			//$location.path('/user');
			$scope.pageChanged();
		});
	};
	
	
	$scope.userenable = function(user){
		user.customPOST({},'active').then(function(){
			$scope.pageChanged();
		});
	}
	$scope.userdisable = function(user){
		user.customDELETE('active').then(function(){
			$scope.pageChanged();
		})
	}
	$scope.userFlipActive = function(user){
		if(user.isActive){
			$scope.userdisable(user);
		}else{
			$scope.userenable(user);
		}
	}
}

function UserCreateCtrl($scope, $location, Restangular) {
	$scope.isEdit = false;
	$scope.save = function() {
		Restangular.all('user').post($scope.user).then(function() {
			$location.path('/user');
		});
	}
}


function UserEditCtrl($scope, $location, Restangular, user) {
  var original = user;
  $scope.isEdit = true;
  $scope.user = Restangular.copy(original);
  $scope.comfirm = user.password;
  
  $scope.isClean = function() {
    return angular.equals(original, $scope.user);
  }

  $scope.save = function() {
    $scope.user.put().then(function() {
      $location.path('/user');
    });
  };
}


function DataPickerCtrl($scope){
	$scope.opened = false;
	
	$scope.open = function($event) {
	    $event.preventDefault();
	    $event.stopPropagation();

	    $scope.opened = true;
	};
}


