function LoginController($scope,$rootScope,$location,Restangular){
	$scope.user = {};
	$scope.user.username="";
	$scope.user.password="";
	
	$scope.login = function(){
		Restangular.all("login").post($scope.user).then(function (loginUser){
			$rootScope.loginUser = loginUser;
			$rootScope.loginUser.logined = true;
			$location.path("/");
		},function(error){
			$rootScope.loginUser = {};
			$rootScope.loginUser.logined = false;
			$root.loginerr = error;
		});
	}
	
}