const app = angular.module("app", []);
app.controller("adminrevenue", function ($scope, $http, $window) {
  $scope.a = function () {
    $http
      .get("/api/admin/revenue")
      .then((resp) => {
        $scope.statistical = resp.data;
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };

  $scope.a();
  // $scope.b = function () {
  // 	$http.get('/api/ad/statistical/pie/year?year=2021').then(resp => {
  // 		$scope.statistical1 = resp.data;
  // 		console.log($scope.statistical1);
  // 	}).catch(error => {
  // 		console.log("errors", error);
  // 	})
  // };

  // $scope.b();
});
