function BodyController($scope,subject,$location,usernamePasswordToken){
	$scope.errauthc = false;

	$scope.token = usernamePasswordToken;

	$scope.logIn = function() {
		subject.login($scope.token).then(function() {
			$scope.errauthc = false;
			$location.path('/');
		}, function(data) {
			$scope.errauthc = true;
		});
	}
	
	$scope.logout = function() {
		subject.logout();
		$location.path('/');
	}
}