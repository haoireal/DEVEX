let host = "http://localhost:8888/rest";
const app = angular.module("app", []);

app.controller("cart-ctrl", function ($scope, $http) {


  //format tiền cho đẹp
  $scope.formatMoney = function (x) {
    var money = "";
    money = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return money;
  };

  // tăng giảm số lượng
  $scope.changeQuantity = function (item, action) {
    if (action === "increase") {
      item.quantity++;
    } else if (action === "decrease") {
      if (item.quantity > 1) {
        item.quantity--;
      }
    }

    // Gọi hàm changeQty để cập nhật giá trị trong giỏ hàng và cơ sở dữ liệu
    $scope.cart.changeQty(item.id, item.quantity);
  };

  // quản lý giỏ hàng
  var $cart = ($scope.cart = {
    items: [],
    itemsOrder: [],
    shopGroups: {},
    selectAll: false,
    selectedShopIds: [],
    sizes: [],
    colors: [],
    selectedProduct:[],
    // Mở Modal chọn size và màu
    openModal: function(product) {
      this.selectedProduct = product; // Sao chép sản phẩm để không ảnh hưởng đến sản phẩm gốc
      var url = `${host}/cart/color/${product.idProduct}`;
      $http.get(url).then((response) => {
            if (response.data === null) {
              this.colors = null;
            }else{
              this.colors = response.data;
            }
        this.selectedProduct.colors = this.colors;
        console.log("độ dài mảng màu là ", this.selectedProduct.colors.length)
      });
      // Khai báo danh sách size và color tương ứng với sản phẩm, ví dụ:
      var url = `${host}/cart/size/${product.idProduct}`;
      $http.get(url).then((response) => {
        if (response.data === null) {
          this.sizes = null;
        }else{
          this.sizes = response.data;
        }
       this.selectedProduct.sizes = this.sizes;
        console.log("độ dài mảng color là ",this.selectedProduct.sizes.length)
      });
      console.log(product.idProduct)
      $('#myModal').modal('show');
    },

    saveSelection: function() {
      let product = this.selectedProduct;
      var item = this.items.find((item) => item.id == id);
      if ($scope.cart.selectedSize === undefined || $scope.cart.selectedSize === null){
        $scope.cart.selectedSize = product.size;
      }
      if ($scope.cart.selectedColor === undefined || $scope.cart.selectedColor === null){
        $scope.cart.selectedColor = product.color;
      }
      console.log("Tên sp sau khi lưu: ",product.idProduct)
      console.log("Size được chọn:", $scope.cart.selectedSize);
      console.log("Màu được chọn:", $scope.cart.selectedColor);
      this.selectedSize =  $scope.cart.selectedSize;
      this.selectedColor = $scope.cart.selectedColor;
      var url = `${host}/cart/changeSizenColor/${product.idProduct}`;
      var data = {
        size: $scope.cart.selectedSize,
        color: $scope.cart.selectedColor
      };
      $http.put(url, data).then((response) => {

      });
      $('#myModal').modal('hide');
    },
    hideModal: function(){
      $('#myModal').modal('hide');
    },
    // load sản phẩm trong giỏ hàng
    loadProductCart() {
      var url = `${host}/cart`;
      $http.get(url).then((response) => {
        this.items = response.data;
        this.groupByShopId();
        console.log(this.items);
        console.log(this.shopGroups);
        console.log(this.count);
      });
    },

    // Thay đổi số lượng sản phẩm trong giỏ hàng khi người dùng thay đổi giá trị số lượng
    changeQty(id, qty) {
      var item = this.items.find((item) => item.id == id);
      if (item) {
        item.quantity = qty;
      }
      // Gọi API để cập nhật  sản phẩm trong cơ sở dữ liệu
      var url = `${host}/cart/${id}`;
      $http
        .put(url, item)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // var index = this.items.findIndex((item) => item.id == id);
          // this.items[index] = resp.data;
          console.log("Success", resp);
          // this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi cập nhật số lượng sản phẩm:", error);
        });
    },

    // Thay đổi số lượng sản phẩm trong giỏ hàng khi người dùng thay đổi giá trị số lượng
    changeSizeColor(id, size, color) {
      var item = this.items[$scope.items.findIndex((item) => item.id == id)];
      // cập nhập new quantity, size, and color
      item.size = size;
      item.color = color;
      // Gọi API để cập nhật  sản phẩm trong cơ sở dữ liệu
      var url = `${host}/cart/${id}`;
      $http
        .put(url, item)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          var index = this.items.findIndex((item) => item.id == id);
          this.items[index] = resp.data;
          console.log("Success", resp);
          this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi cập nhật số lượng sản phẩm:", error);
        });
    },

    remove(id) {
      // xóa sản phẩm khỏi giỏ hàng
      // Gọi API để xóa sản phẩm khỏi cơ sở dữ liệu
      var url = `${host}/cart/${id}`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // Sau khi xoá thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng trên frontend
          // this.items = this.items.filter((item) => item.id !== id);
          console.log("Success", resp);
          this.itemsOrder = this.itemsOrder.filter((item) => item.id !== id);
          this.loadProductCart();
          // this.amount();
        })
        .catch((error) => {
          console.log("Lỗi khi xoá sản phẩm khỏi giỏ hàng:", error);
        });
    },

    removeAllOfShop(idShop) {
      // Gọi API để cập nhật giỏ hàng trong cơ sở dữ liệu
      var url = `${host}/cart/shop/${idShop}`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          // this.items = response.data;
          this.itemsOrder = this.itemsOrder.filter(
            (item) => item.idShop !== idShop
          );
          console.log("Success", resp);
          this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error(
            "Lỗi khi xoá tất cả sản phẩm của shop khỏi giỏ hàng:",
            error
          );
        });
    },
    clear() {
      // Xóa sạch các mặt hàng trong giỏ
      this.clearAllItems(); // Gán mảng rỗng để xóa sạch các mặt hàng trong giỏ hàng
      // Gọi API để cập nhật giỏ hàng trong cơ sở dữ liệu
      var url = `${host}/cart`;
      $http
        .delete(url)
        .then((resp) => {
          // Xử lý phản hồi từ server nếu cần thiết
          this.items = response.data;
          console.log("Success", resp);
          this.loadProductCart();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi xóa sạch các mặt hàng trong giỏ hàng:", error);
        });
    },

    clearAllItems() {
      this.items = [];
      this.shopGroups = {};
      this.itemsOrder = [];
    },

    amt_of(item) {
      // tính thành tiền của 1 sản phẩm
      return item.price * item.quantity;
    },
    get count() {
      // tính tổng số lượng các mặt hàng trong giỏ
      return this.items
        .map((item) => item.quantity)
        .reduce((total, qty) => (total += qty), 0);
    },
    
    get countItem() {
		return this.items.length;

	},

    get countItemOrder() {
      // tính tổng số lượng các mặt hàng trong giỏ
      return this.itemsOrder
        .map((item) => item.quantity)
        .reduce((total, qty) => (total += qty), 0);
    },

    get amount() {
      // tổng thành tiền các mặt hàng trong giỏ
      return this.itemsOrder
        .map((item) => this.amt_of(item))
        .reduce((total, amt) => (total += amt), 0);
    },

    isItemChecked(id) {
      return this.itemsOrder.some((item) => item.id === id);
    },

    //check sp để thanh toán
    toggleItemOrder(id) {
      var index = this.itemsOrder.findIndex((item) => item.id == id);
      if (index !== -1) {
        this.itemsOrder.splice(index, 1);
        this.selectAll = false; // Bỏ chọn "Chọn tất cả" nếu có ít nhất một sản phẩm không được chọn
      } else {
        var item = this.items.find((item) => item.id == id);
        if (item) {
          this.itemsOrder.push(item);
        }
      }

      console.log(this.items.length);
      console.log(this.itemsOrder.length);
      console.log(this.items);
      console.log(this.itemsOrder);
      if (this.items.length === this.itemsOrder.length) {
        this.selectAll = true;
      }
    },

    toggleSelectAll() {
      this.selectAll = !this.selectAll;
      if (this.selectAll) {
        this.itemsOrder = angular.copy(this.items);
        console.log(this.itemsOrder);
        this.selectedShopIds = Object.keys(this.shopGroups);
      } else {
        this.itemsOrder = [];
        this.selectedShopIds = [];
      }
    },

    toggleShopSelect(shopId) {
      var index = this.selectedShopIds.indexOf(shopId);
      if (index !== -1) {
        this.selectedShopIds.splice(index, 1);
        this.itemsOrder = this.itemsOrder.filter(
          (item) => item.idShop !== shopId
        );
        this.selectAll = false;
      } else {
        this.selectedShopIds.push(shopId);
        this.itemsOrder = this.itemsOrder.concat(
          this.items.filter((item) => item.idShop === shopId)
        );
        console.log(this.itemsOrder);
        if (
          this.selectedShopIds.length === Object.keys(this.shopGroups).length
        ) {
          this.selectAll = true;
        }
      }
    },

    groupByShopId() {
      this.shopGroups = {};
      this.items.forEach((item) => {
        if (!this.shopGroups[item.idShop]) {
          this.shopGroups[item.idShop] = [];
        }
        this.shopGroups[item.idShop].push(item);
      });
    },
  });

  $cart.loadProductCart();

  // Đặt hàng

  $scope.checkAndPerformAction = function () {
    var userAddress = document.getElementById("userAddress").value;
    var userPhoneAddress = document.getElementById("userPhoneAddress").value;
    if ($cart.itemsOrder.length === 0) {
      alert("Vui lòng chọn sản phẩm!");
    } else if (userAddress === "" || userPhoneAddress === "") {
      this.showAlert();
    } else {
      this.purchase();
    }
  };

  $scope.showAlert = function () {
    alert(
      "Vui lòng cập nhật thông tin địa chỉ và số điện thoại trước khi mua hàng!"
    );
  };

  $scope.purchase = function () {
    // Thực hiện đặt hàng
    var url = `${host}/cart/order`;
    $http
      .post(url, $cart.itemsOrder)
      .then((resp) => {
        alert("Đặt hàng thành công!");
        console.log(resp);
        $cart.selectAll = true;
        $cart.toggleSelectAll();
        $cart.loadProductCart();
      })
      .catch((error) => {
        alert("Đặt hàng lỗi!");
        console.log(error);
      });
  };
});
