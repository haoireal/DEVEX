const app = angular.module("app", []);
app.controller("requestproduct", function ($scope, $http, $location, $window) {
  $scope.listProductRequest = [];

  // get all ProductRequest
  $scope.getAllProductRequest = function () {
    $http.get("/api/getallproductrequest").then(
      function (response) {
        $scope.listProductRequest = response.data;
      },
      function (error) {
        console.error("Error:", error);
      }
    );
  };

  // delete ProductRequest
  $scope.cancelRequest = function (id) {
    $http
      .delete("/api/delete/productrequest?id=" + id)
      .then(function (response) {
        $scope.listProductRequest = response.data;
      });
  };

  // confirm ProductRequest
  $scope.confirmRequest = function (id) {
    $http.put("/api/update/productrequest?id=" + id).then(function (response) {
      $scope.listProductRequest = response.data;
    });
  };

  // focus URL
  $scope.focusURL = function (id) {
    $http
      .get("/api/idproductrequest")
      .then(function (response) {
        console.log(response.data);
        if (response.data == 0) {
          $window.location.href = "/ad/showproduct/" + id;
        }
      })
      .catch(function (error) {
        console.error("Error making API request:", error);
        // Xử lý lỗi nếu cần thiết
      });
  };

  $scope.getAllProductRequest();
});
