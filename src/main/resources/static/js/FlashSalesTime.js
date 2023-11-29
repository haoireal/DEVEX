const app = angular.module("app", []);
app.controller("myController", function ($scope, $http) {
  var table;
  $scope.data = [];
  $scope.flashSalesTime = function () {
    $http
      .get("/api/flashSales")
      .then((resp) => {
        $scope.data = resp.data;
        // console.log($scope.data)
        initTableData($scope.data);
      })
      .catch(function (err) {
        console.error(err); // xử lý lỗi khi gọi API
        // alert('Có lỗi xảy ra khi gọi API'); // hiển thị thông báo lỗi cho người dùng
      });
  };

  $scope.flashSalesTime(); // lấy dữ

  function initTableData(data) {
    // Kiểm tra xem DataTable đã được khởi tạo chưa
    if ($.fn.dataTable.isDataTable("#FlashTime")) {
      // Nếu DataTable đã tồn tại, hủy bỏ nó trước
      $("#FlashTime").DataTable().destroy();
    }
    // Khởi tạo DataTable mới với dữ liệu mới
    table = $("#FlashTime").DataTable({
      processing: true,
      data: $scope.data,
      columns: [{ data: "id" }, { data: "firstTime" }, { data: "lastTime" }],
    });
  }

  $scope.saveData = function () {
    // Lấy giá trị từ các ô input
    var date = new Date($scope.formData.date);
    var firstTime = new Date($scope.formData.firstTime);
    var lastTime = new Date($scope.formData.lastTime);

    var dateFormatted =
      date.getMonth() + 1 + "/" + date.getDate() + "/" + date.getFullYear();
    var firstTimeFormatted =
      (firstTime.getHours() > 12
        ? firstTime.getHours() - 12
        : firstTime.getHours()) +
      ":" +
      (firstTime.getMinutes() < 10 ? "0" : "") +
      firstTime.getMinutes() +
      (firstTime.getHours() >= 12 ? " PM" : " AM");
    var lastTimeFormatted =
      (lastTime.getHours() > 12
        ? lastTime.getHours() - 12
        : lastTime.getHours()) +
      ":" +
      (lastTime.getMinutes() < 10 ? "0" : "") +
      lastTime.getMinutes() +
      (lastTime.getHours() >= 12 ? " PM" : " AM");

    // Dữ liệu để gửi lên server
    var dataToSend = {
      firstTime: firstTimeFormatted + "" + dateFormatted,
      lastTime: lastTimeFormatted + "" + dateFormatted,
    };

    // Sử dụng Fetch API để gửi dữ liệu lên server
    $http
      .post("/api/saveFlashSales", dataToSend)
      .then((response) => {
        //  alert("ngon");/**/
        $scope.flashSalesTime(); // lấy dữ
      })
      .catch((error) => {
        console.error(error);
        alert("Có lỗi xảy ra khi cập nhật roles");
      });
  };
}); // end controller
