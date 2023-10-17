var currentDate = new Date();
var year = currentDate.getFullYear();
var month = currentDate.getMonth();

const app = angular.module("app", []);
app.controller("myController", function ($scope, $http) {
  // JavaScript để mở và đóng modal
  var modal = document.getElementById("myModal");
  var modal1 = document.getElementById("myModal1");
  // Khai báo biến để lưu giá trị radio cuối cùng được chọn
  var lastSelectedValue = null;
  var selectedCheckBoxValues = [];
  $scope.getDay = 0;
  $scope.month = month + 1;
  $scope.year = year;
  $scope.data = []; // nhận thời gian
  $scope.selectedItem = [];
  $scope.dataProduct = []; // product của shop
  $scope.dataProductFlashSales = []; // product tham gia Flash sales
  $scope.openModal = function () {
    modal.style.display = "block";
    // Gọi hàm để tạo lịch
    createCalendar();
  };
  $scope.closeModel = function () {
    modal.style.display = "none";
  };

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
    if (event.target == modal1) {
      modal1.style.display = "none";
    }
  };
  // modal thêm sản phẩm
  $scope.openModal1 = function () {
    $scope.sellerProduct();
    // initTableData($scope.dataProduct);
    modal1.style.display = "block";
  };
  $scope.closeModel1 = function () {
    modal1.style.display = "none";
  };

  // Hàm để tạo lịch
  createCalendar = function () {
    var calendar = document.getElementById("calendar");

    var daysInMonth = new Date(year, month + 1, 0).getDate();

    var html = "<table id='myCalendar'> ";
    html +=
      "<tr class= 'header_calender'>  <th colspan='1' ><i class='fa-solid fa-angles-left'></i>  <i class='fa-solid fa-angle-left'></i></th>  <th colspan='5'>" +
      (month + 1) +
      "/" +
      year +
      " </th> <th colspan='1' ><i class='fa-solid fa-angle-right'></i>  <i class='fa-solid fa-angles-right'></i></th></tr>";
    html +=
      "<tr><th>CN</th><th>T2</th><th>T3</th><th>T4</th><th>T5</th><th>T6</th><th>T7</th></tr>";

    var dayOfWeek = new Date(year, month, 1).getDay();
    var day = 1;

    for (var i = 0; i < 6; i++) {
      html += "<tr>";
      for (var j = 0; j < 7; j++) {
        if ((i === 0 && j < dayOfWeek) || day > daysInMonth) {
          html += "<td ></td>";
        } else {
          // Sử dụng lớp CSS selected-day để xác định ngày được chọn
          html +=
            "<td class='selected-day' onclick='selectDate(this, " +
            day +
            ")'>" +
            day +
            "</td>";
          day++;
        }
      }
      html += "</tr>";
      if (day > daysInMonth) {
        break;
      }
    }

    html += "</table>";

    calendar.innerHTML = html;
  };
  // Hàm để xử lý sự kiện khi chọn ngày
  // Hàm để xử lý sự kiện khi chọn ngày
  selectDate = function (element, day) {
    // Xoá màu viền màu đỏ ở tất cả các ngày
    var selectedDays = document.querySelectorAll(".selected-day");
    for (var i = 0; i < selectedDays.length; i++) {
      selectedDays[i].style.borderColor = "";
    }

    // Đặt màu viền của ngày mới được chọn thành màu đỏ
    element.style.borderColor = "red";
    $scope.getDay = day;
    // console.log($scope.getDay);
    $scope.saveData();
  };

  $scope.saveData = function () {
    // Dữ liệu để gửi lên server
    var dataToSend = {
      day: $scope.getDay,
      month: $scope.month,
      year: $scope.year,
    };
    console.log(dataToSend);
    // console.log("selectedItem"+ $scope.selectedItem);
    // Sử dụng Fetch API để gửi dữ liệu lên server
    $http
      .post("/api/flashTime", dataToSend)
      .then((response) => {
        //  alert("ngon");/**/
        $scope.data = response.data;
        console.log($scope.data);
      })
      .catch((error) => {
        console.error(error);
        alert("Có lỗi xảy ra khi cập nhật roles");
      });
  };

  findItemById = function (id) {
    return $scope.data.find((item) => item.id === id);
  };

  $scope.saveTime = function () {
    // Lấy danh sách tất cả các input có thuộc tính "name" là "time"
    var timeInputs = document.querySelectorAll('input[name="time"]');

    // Duyệt qua danh sách các input
    for (var i = 0; i < timeInputs.length; i++) {
      if (timeInputs[i].type === "radio" && timeInputs[i].checked) {
        lastSelectedValue = timeInputs[i].value;
      }
    }

    // Kiểm tra xem lastSelectedValue có giá trị không rỗng
    if (lastSelectedValue !== null) {
      // console.log("Giá trị radio trước được chọn là: " + JSON.stringify( $scope.selectedItem));
      $scope.selectedItem = findItemById(parseInt(lastSelectedValue));
      // console.log(lastSelectedValue)
      // In ra giá trị radio cuối cùng được chọn
      console.log(
        "Giá trị radio cuối cùng được chọn là: " +
          JSON.stringify($scope.selectedItem)
      );
    } else {
      console.log("Không có radio nào được chọn.");
    }
    $scope.closeModel();
  };

  // Hàm này sẽ được gọi khi có thay đổi trạng thái của checkbox

  toggleCheckboxes = function () {
    var checkedAll = document.getElementById("checkedALL");
    var flashSaleProducts = document.querySelectorAll(".flash_sale_product");

    for (var i = 0; i < flashSaleProducts.length; i++) {
      flashSaleProducts[i].checked = checkedAll.checked;
    }
  };

  // thêm sản phẩm

  $scope.sellerProduct = function () {
    // hiện th
    $http
      .get("/api/sellerProduct")
      .then((resp) => {
        $scope.dataProduct = resp.data;
        console.log($scope.dataProduct);
      })
      .catch(function (err) {
        console.error(err); // xử lý lỗi khi gọi API
        // alert('Có lỗi xảy ra khi gọi API');
      });
  };
  // lay ID và filter sp

  $scope.saveProduct = function () {
    var flashSaleProducts = document.querySelectorAll(".flash_sale_product");

    for (var i = 0; i < flashSaleProducts.length; i++) {
      if (
        flashSaleProducts[i].type === "checkbox" &&
        flashSaleProducts[i].checked
      ) {
        selectedCheckBoxValues.push(parseInt(flashSaleProducts[i].value));
      }
    }
    console.log("Giá trị được chọn là: " + selectedCheckBoxValues);
    selectedCheckBoxValues.forEach(() => {
      // Sử dụng hàm filter để lọc sản phẩm có productVariants thỏa mãn điều kiện
      $scope.dataProductFlashSales = $scope.dataProduct.filter((item) => {
        let productVariants = item.productVariants;

        // Sử dụng hàm some để kiểm tra xem có ít nhất một productVariant có ID trong selectedCheckBoxValues
        return productVariants.some((variant) => {
          // console.log(selectedCheckBoxValues);
          return selectedCheckBoxValues.includes(variant.id);
        });
      });
    });

    console.log($scope.dataProductFlashSales);
    $scope.closeModel1();
  };
  // $scope.discount  = 0; // Ban đầu, giá trị priceSale là 0 hoặc giá trị mặc định của bạn
  // // $scope.priceSale = 0;
  // $scope.price = null;
  // $scope.handlePriceChange = function() {
  //   // Lấy giá trị từ ô input priceSale và chuyển đổi sang số
  //   var inputValue = document.getElementById("priceSale").value;

  //   // var priceSaleValue = $scope.priceSale;
  //   console.log(inputValue); // In ra giá trị trong console  // console.log( $scope.priceSale);
  //   const originalPrice = $scope.dataProductFlashSales.productVariants.price;
  //   console.log(originalPrice); // In ra giá tr
  //   // const priceSale = $scope.priceSale;
  //   // $scope.discount = ((originalPrice - priceSale) / originalPrice) * 100;

  // };

  $scope.getDataTable = function () {
    // Khai báo mảng để chứa các đối tượng dataFlashSale
    var dataFlashSales = [];

    // Lấy giá trị của các phần tử HTML và tạo các đối tượng dataFlashSale
    var priceSaleElements = document.querySelectorAll("#priceSale");
    var amountSellElements = document.querySelectorAll("#amountSell");
    var amountOrderElements = document.querySelectorAll("#amountOrder");
    var statusElements = document.querySelectorAll("#status");

    // Ví dụ: Sử dụng vòng lặp để tạo các đối tượng và thêm vào mảng dataFlashSales
    for (var i = 0; i < priceSaleElements.length; i++) {
      var dataFlashSale = {
        discount: priceSaleElements[i].value,
        amountSell: amountSellElements[i].value,
        amountOrder: amountOrderElements[i].value,
        status: statusElements[i].checked,
        fashSaleTimeId: parseInt(lastSelectedValue),
        productVariantId: selectedCheckBoxValues[i],
        // Các trường khác của dataFlashSale
      };
      // console.log("id" + selectedCheckBoxValues[i]);
      dataFlashSales.push(dataFlashSale);
    }

    // Gửi mảng dataFlashSales về máy chủ để xử lý logic
    // Ví dụ: Sử dụng AJAX hoặc fetch để gửi dữ liệu về máy chủ

    console.log(dataFlashSales);
    // console.log(dataFlashSale);
    $http
      .post("/api/setFlashSale", dataFlashSales)
      .then(() => {
        alert("success");
        var dataFlashSales = [];
      })
      .catch((error) => {
        console.error(error);
        alert("Có lỗi xảy ra khi cập nhật roles");
      });
  };
}); // end controller
