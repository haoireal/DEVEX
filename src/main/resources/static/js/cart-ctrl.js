const app = angular.module("app", []);

app.controller("cart-ctrl", function ($scope, $http) {
  // quản lý giỏ hàng
  var $cart = ($scope.cart = {
    items: [],
    shopGroups: {},
    // load sản phẩm trong giỏ hàng
    loadProductCart() {
      $http.get("/rest/cart").then(function (response) {
        this.items = response.data;
        this.groupByShopId();
      });
    },

    // Thay đổi số lượng sản phẩm trong giỏ hàng khi người dùng thay đổi giá trị số lượng
    changeQty(id, qty) {
      var item = this.items[$scope.items.findIndex((item) => item.id == id)];
      // cập nhập new quantity, size, and color
      item.quantity = qty;
      // Gọi API để cập nhật  sản phẩm trong cơ sở dữ liệu
      $http
        .put(`/rest/cart/${id}`, item)
        .then(function (resp) {
          // Xử lý phản hồi từ server nếu cần thiết
          var index = this.items.findIndex((item) => item.id == id);
          this.items[index] = resp.data;
          console.log("Success", resp);
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
      $http
        .put(`/rest/cart/${id}`, item)
        .then(function (resp) {
          // Xử lý phản hồi từ server nếu cần thiết
          var index = this.items.findIndex((item) => item.id == id);
          this.items[index] = resp.data;
          console.log("Success", resp);
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi cập nhật số lượng sản phẩm:", error);
        });
    },

    remove(id) {
      // xóa sản phẩm khỏi giỏ hàng
      // Gọi API để xóa sản phẩm khỏi cơ sở dữ liệu
      $http
        .delete(`/rest/cart/${id}`)
        .then(function (resp) {
          // Xử lý phản hồi từ server nếu cần thiết
          console.log("Success", resp);
          // Sau khi xoá thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng trên frontend
          this.items = this.items.filter((item) => item.id !== id);
          this.groupByShopId();
          // $scope.cart.saveToLocalStorage();
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi xoá sản phẩm khỏi giỏ hàng:", error);
        });
    },
    removeAllOfShop(idShop) {
      //xử lý
      this.items = this.items.filter((item) => item.shopId !== idShop);
      // Gọi API để cập nhật giỏ hàng trong cơ sở dữ liệu
      $http
        .put(`/rest/cart`, this.items)
        .then(function (resp) {
          // Xử lý phản hồi từ server nếu cần thiết
          this.items = response.data;
          this.groupByShopId();
          console.log("Success", resp);
          console.log(
            "Đã xoá tất cả sản phẩm của shop khỏi giỏ hàng thành công!"
          );
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
      $http
        .put(`/rest/cart`, this.items)
        .then(function (resp) {
          // Xử lý phản hồi từ server nếu cần thiết
          this.items = response.data;
          console.log("Success", resp);
          console.log("Đã xóa sạch các mặt hàng trong giỏ hàng!");
        })
        .catch(function (error) {
          // Xử lý lỗi nếu có
          console.error("Lỗi khi xóa sạch các mặt hàng trong giỏ hàng:", error);
        });
    },

    clearAllItems() {
      this.items = [];
      this.shopGroups = {};
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
    get amount() {
      // tổng thành tiền các mặt hàng trong giỏ
      return this.items
        .map((item) => this.amt_of(item))
        .reduce((total, amt) => (total += amt), 0);
    },

    groupByShopId() {
      this.shopGroups = {};
      this.items.forEach((item) => {
        if (!this.shopGroups[item.shopId]) {
          this.shopGroups[item.shopId] = [];
        }
        this.shopGroups[item.shopId].push(item);
      });
    },
  });

  $cart.loadProductCart();

  // Đặt hàng
  $scope.order = {
    get account() {
      return { username: $auth.user.username };
    },
    createDate: new Date(),
    address: "",
    get orderDetails() {
      return $cart.items.map((item) => {
        return {
          product: { id: item.id },
          price: item.price,
          quantity: item.qty,
        };
      });
    },
    purchase() {
      var order = angular.copy(this);
      // Thực hiện đặt hàng
      $http
        .post("/rest/orders", order)
        .then((resp) => {
          alert("Đặt hàng thành công!");
          $cart.clear();
          location.href = "/order/detail/" + resp.data.id;
        })
        .catch((error) => {
          alert("Đặt hàng lỗi!");
          console.log(error);
        });
    },
  };
});
