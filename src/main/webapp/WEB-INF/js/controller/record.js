
function dot2_(ip){
	return ip.replace(/\./g,"_");
}

function RecordListCtrl($scope,$location, Restangular) {
	$scope.searchby = 'account';
	$scope.key = '';
	
	var searchpath = function(s){
		return s!=''?"/"+s:"";
	}
	
	$scope.page = {number:1,size:10};
	$scope.pageChanged = function(){
		var temp = $scope.key;
		if($scope.searchby=="userip" || $scope.searchby=="nasip"){
			temp = dot2_(temp);
		}
		var searchpath = ($scope.searchby!=''&&$scope.key!='')?"/"+$scope.searchby+"/"+temp:null;
		var searchpath = escape(searchpath==null?"":searchpath);
		Restangular.all("record").customGETLIST(searchpath,{'page':$scope.page.number-1,'size':$scope.page.size}).then(function(recordlist){
			$scope.recordlist = recordlist;
			$scope.page = recordlist.page;
			$scope.page.number = $scope.page.number + 1;
		},function(err){
			alert(err);
		});
	}
	
	
	$scope.pageChanged();//first call
}



