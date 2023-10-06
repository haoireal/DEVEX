// // const app = angular.module("app", []);
// app.controller("myController", function ($scope, $http, $window) { 
//     alert("ngu")
//     $scope.data = [];
//     $scope.filter = function () {			
//         $http.get('/api/filter').then(resp => {
//           $scope.data = resp.data;
//           console.log($scope.data)  
        
//         }).catch(function(err ) {
//           console.error(err); // xử lý lỗi khi gọi API
//           // alert('Có lỗi xảy ra khi gọi API'); // hiển thị thông báo lỗi cho người dùng
//         });
          
//       };
    
//       $scope.filter();// lấy dữ liệu shop



// });// end controller