const app = angular.module("app", []);
app.controller("sellerrevenue", function ($scope, $http, $window) {
  $scope.total = function () {
    $http
      .get("/seller/revenue/gettotalprice")
      .then((resp) => {
        $scope.statistical = resp.data;
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };
  $scope.a = function () {
    $http
      .get("/rest/list/order?year=2023&month=9&trangthai=daxacnhan")
      .then((resp) => {
        $scope.a = resp.data;
        console.log($scope.a);
      })
      .catch((error) => {
        console.log("errors", error);
      });
  };

  $scope.a();
  $scope.total();
});
