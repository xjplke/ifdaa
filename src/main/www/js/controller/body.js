function BodyController($scope,subject,$location,usernamePasswordToken,localStorageService,$rootScope){
	$scope.errauthc = false;

	$scope.token = usernamePasswordToken;
	$scope.tmp = {};
	
	$scope.logIn = function() {
		
		$scope.tmp.username = $scope.token.username;
		$scope.tmp.password = $scope.token.password;
		$rootScope.global.cookieLogin = false;
		subject.login($scope.token).then(function() {
			$scope.errauthc = false;
			$location.path('/');
			localStorageService.bind($scope,"token",$scope.tmp);
		}, function(data) {
			$scope.errauthc = true;
		});
	}
	
	$scope.logout = function() {
		localStorageService.remove("token");
		subject.logout();
		$location.path('/');
	}
}