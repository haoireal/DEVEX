
const app = angular.module("app", []);
app.controller("myController", function ($scope, $http, $window) { 
  $scope.data = []; // khởi tạo biến $scope.fill với một mảng rỗng

  $scope.fillSeller = function () {			
    $http.get('/api/shop').then(resp => {
      $scope.data = resp.data;
      console.log($scope.data)  
    }).catch(function(err) {
      console.error(err); // xử lý lỗi khi gọi API
      // alert('Có lỗi xảy ra khi gọi API'); // hiển thị thông báo lỗi cho người dùng
    });

  };

  $scope.fillSeller();// lấy dữ liệu shop


  $scope.updateShop = function () {

    var data = {
      shopName: $scope.data.seller.shopName,
      address: $scope.data.seller.address,
      phoneAddress: $scope.data.seller.phoneAddress,
      mall: $scope.data.seller.mall
  };
  console.log(data);
    $http.post('/api/updateShop', data).then(response => {
        // Xử lý kết quả sau khi cập nhật thành công
        alert(response.data.message);
    }).catch(error => {
        console.error('Có lỗi xảy ra khi cập nhật', error);
        // Xử lý lỗi nếu có
    });
  };


});





