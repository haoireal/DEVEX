let host = "http://localhost:8888/rest";
const app = angular.module("app", ["ngRoute"]);

app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.hashPrefix('');
}]);

app.controller("cart-ctrl", function($scope, $http, $location, $window) {


	//format tiền cho đẹp
	$scope.formatMoney = function(x) {
		var money = "";
		money = x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
		return money;
	};

	$scope.formatDateToDDMMYYYY = function(dateString) {
		const date = new Date(dateString); // Chuyển chuỗi thời gian thành đối tượng Date
		const options = { day: 'numeric', month: 'numeric', year: 'numeric' };
		return date.toLocaleDateString('vi-VN', options); // Chuyển đổi sang "dd/MM/yyyy"
	};

	$scope.formatDateTimeToDDMMYYYYHHMM = function(dateString) {
		const date = new Date(dateString);
		const options = {
			day: 'numeric',
			month: 'numeric',
			year: 'numeric',
			hour: 'numeric',
			minute: 'numeric',
		};
		return date.toLocaleDateString('vi-VN', options);
	};

	// tăng giảm số lượng
	$scope.changeQuantity = function(item, action) {
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
		itemsOrderSession: [],
		shopGroups: {},
		shopGroupsOrder: {},
		selectAll: false,
		selectedShopIds: [],
		sizes: [],
		colors: [],
		selectedProduct: [],

		//	Voucher
		voucherAll: [],
		voucherShop: [],
		voucherDevex: [],
		voucherShipping: [],
		voucherApply: [],
		myVoucher: [],
		myVoucherManage: [],

		// voucher trong kho quản lý khách hàng
		

		// lưu voucher
		saveVoucher(item) {
			var url = `${host}/voucher/save`;
			$http.post(url, item).then((response) => {
				console.log(this.voucherAll);
				this.loadVoucherOfUser(); 
			});
		},

		// áp dụng voucher
		applyVoucher(item) {
			this.voucherApply.push[item];
		},

		// mở modal voucher của shop
		openModalVoucherShop: function(idShop) {
			this.groupVoucherShop(idShop);
			console.log(idShop);
			$('#voucherOfShop').modal('show');
		},

		// mở modal voucher của devex
		openModalVoucherDevex: function() {
			this.groupVoucherDevex();
			$('#voucherOfDevex').modal('show');
		},

		// load voucher
		loadVoucherOfUser() {
			var url = `${host}/voucher/saved/list`;
			$http.get(url).then((response) => {
				this.myVoucher = response.data;
				console.log(this.myVoucher);
				// console.log(this.myVoucher[0].voucher);
			});
		},

		// check voucher có sở hữu hay chưa
		isItemInMyVoucher(item) {
			//Kiểm tra xem item.id có tồn tại trong myVoucher hay không
			return this.myVoucher.some(voucher => voucher.voucher.id === item.id);
		},

		isItemInMyVoucherApplied(item) {
			// Kiểm tra xem item.id có tồn tại trong myVoucher và voucher.id có applied hay không
			return this.myVoucher.some(voucher => voucher.voucher.id === item.id && !voucher.applied === true);
		},

		// load voucher
		loadVoucher() {
			var url = `${host}/voucher/list`;
			$http.get(url).then((response) => {
				this.voucherAll = response.data;
				console.log(this.voucherAll);
			});
		},

		sortCreatedDateAllItem(array) {
			// Sắp xếp danh sách theo ngày tạo mới nhất (giảm dần)
			return array.sort((a, b) => {
				const dateA = new Date(a.createdDay);
				const dateB = new Date(b.createdDay);
				return dateB - dateA;
			});
		},

		// group voucher của shop đang diễn ra
		groupVoucherShop(idShop) {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher đang diễn ra
			this.voucherShop = this.voucherAll.filter(item => {
				const startDate = new Date(item.startDate);
				const endDate = new Date(item.endDate);
				return startDate <= currentDate && currentDate <= endDate && item.active === true && item.creator.username === idShop;
			});

			this.sortCreatedDateAllItem(this.voucherShop);

			console.log(this.voucherShop);
		},

		// group voucher của devex
		groupVoucherDevex() {
			const currentDate = new Date(); // Lấy ngày hiện tại

			// Lọc danh sách các voucher devex đang diễn ra
			this.voucherDevex = this.voucherAll.filter(item => {
				const startDate = new Date(item.startDate);
				const endDate = new Date(item.endDate);
				return startDate <= currentDate && currentDate <= endDate && item.active === true && item.categoryVoucher.id === 100001;
			});

			this.sortCreatedDateAllItem(this.voucherDevex);

			// Lọc danh sách các voucher ship đang diễn ra
			this.voucherShipping = this.voucherAll.filter(item => {
				const startDate = new Date(item.startDate);
				const endDate = new Date(item.endDate);
				return startDate <= currentDate && currentDate <= endDate && item.active === true && item.categoryVoucher.id === 100002;
			});

			this.sortCreatedDateAllItem(this.voucherShipping);

			console.log(this.voucherDevex);
		},


		// Mở Modal chọn size và màu
		openModal: function(product) {
			//reset lại trạng thái của radio
			$scope.cart.selectedSize = null;
			$scope.cart.selectedColor = null;
			this.selectedProduct = product; // Sao chép sản phẩm để không ảnh hưởng đến sản phẩm gốc
			var url = `${host}/cart/color/${product.idProduct}`;
			$http.get(url).then((response) => {
				if (response.data === null) {
					this.colors = null;
				} else {
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
				} else {
					this.sizes = response.data;
				}
				this.selectedProduct.sizes = this.sizes;
				console.log("độ dài mảng size là ", this.selectedProduct.sizes.length)
			});
			console.log(product.idProduct)

			$('#myModal').modal('show');
		},

		saveSelection: function() {

			let cartDetailPd = this.selectedProduct;

			if ($scope.cart.selectedSize === undefined || $scope.cart.selectedSize === null) {
				$scope.cart.selectedSize = cartDetailPd.size;
			}
			if ($scope.cart.selectedColor === undefined || $scope.cart.selectedColor === null || this.selectedProduct.colors.length === 1) {
				$scope.cart.selectedColor = cartDetailPd.color;
			}
			console.log("Tên sp sau khi lưu: ", cartDetailPd.idProduct)
			console.log("Size được chọn:", $scope.cart.selectedSize);
			console.log("Màu được chọn:", $scope.cart.selectedColor);
			console.log(cartDetailPd);
			this.selectedSize = $scope.cart.selectedSize;
			this.selectedColor = $scope.cart.selectedColor;
			var url = `${host}/cart/changeSizenColor/${cartDetailPd.idProduct}?cartDetailId=${cartDetailPd.id}`;
			var data = {
				size: $scope.cart.selectedSize,
				color: $scope.cart.selectedColor
			};
			$http.put(url, data).then((response) => {
				this.loadProductCart();
				console.log("Success", response);
			});
			$('#myModal').modal('hide');
		},
		hideModal: function() {
			$('#myModal').modal('hide');
		},
		// load sản phẩm trong giỏ hàng
		loadProductCart() {
			var url = `${host}/cart`;
			$http.get(url).then((response) => {
				this.items = response.data;
				this.groupByShopId();
				this.groupByOrders();
				console.log(this.items);
				console.log(this.shopGroups);
				console.log(this.shopGroupsOrder);
				console.log(this.count);
			});
		},

		// Thay đổi số lượng sản phẩm trong giỏ hàng khi người dùng thay đổi giá trị số lượng
		changeQty(id, qty) {

			var item = this.items.find((item) => item.id == id);
			if (item) {
				item.quantity = qty;
			}
			console.log(id)
			console.log(item.id)
			// Gọi API để cập nhật  sản phẩm trong cơ sở dữ liệu
			var url = `${host}/cart/${id}`;
			$http
				.put(url, item)
				.then((resp) => {
					// Xử lý phản hồi từ server nếu cần thiết
					// var index = this.items.findIndex((item) => item.id == id);
					this.items[index] = resp.data;
					console.log("Success", resp);
					//					 this.loadProductCart();
					//					this.countItemOrder();
					//					this.amount();

				})
				.catch(function(error) {
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
				.catch(function(error) {
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
				.catch(function(error) {
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

		get countItemOrderDetail() {
			// lấy sản phẩm từ session
			//			this.itemsOrderSession = JSON.parse(sessionStorage.getItem('itemsOrder')) || [];
			// tính tổng số lượng các mặt hàng trong giỏ
			return this.itemsOrderSession
				.map((item) => item.quantity)
				.reduce((total, qty) => (total += qty), 0);
		},

		get amount() {
			// tổng thành tiền các mặt hàng trong giỏ
			return this.itemsOrder
				.map((item) => this.amt_of(item))
				.reduce((total, amt) => (total += amt), 0);
		},

		get amountDetail() {
			// lấy sản phẩm từ session
			//			this.itemsOrderSession = JSON.parse(sessionStorage.getItem('itemsOrder')) || [];
			// tổng thành tiền các mặt hàng trong giỏ
			return this.itemsOrderSession
				.map((item) => this.amt_of(item))
				.reduce((total, amt) => (total += amt), 0);
		},

		isItemChecked(id) {

			return this.itemsOrder.some((item) => item.id === id);
		},

		getCountItemsByShopId(shopId) {
			// Lọc ra các item có cùng shopId
			const itemsWithSameShopId = this.itemsOrder.filter((item) => item.idShop === shopId);

			// Tính tổng số lượng của các item đã lọc
			const totalQuantity = itemsWithSameShopId.length;
			console.log(totalQuantity);
			return totalQuantity;
		},

		//check sp để thanh toán
		toggleItemOrder(id) {
			var index = this.itemsOrder.findIndex((item) => item.id == id);
			if (index !== -1) {

				var indexExtra = this.selectedShopIds.indexOf(this.itemsOrder[index].idShop);
				this.selectedShopIds.splice(indexExtra, 1);
				console.log(indexExtra);
				console.log(this.selectedShopIds);
				this.itemsOrder.splice(index, 1);
				this.selectAll = false; // Bỏ chọn "Chọn tất cả" nếu có ít nhất một sản phẩm không được chọn
			} else {
				var item = this.items.find((item) => item.id == id);
				if (item) {
					this.itemsOrder.push(item);
					console.log(this.shopGroups[item.idShop].length);
					if (this.shopGroups[item.idShop].length === this.getCountItemsByShopId(item.idShop)) {
						this.selectedShopIds.push(item.idShop);
					}
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
				this.itemsOrder = this.items;
				console.log(this.itemsOrder);
				this.selectedShopIds = Object.keys(this.shopGroups);
				console.log(this.selectedShopIds);
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

		groupByOrders() {
			//			sessionStorage.setItem('itemsOrder', JSON.stringify($cart.itemsOrder));
			this.shopGroupsOrder = {};
			this.itemsOrderSession = JSON.parse(sessionStorage.getItem('itemsOrder')) || [];
			this.itemsOrderSession.forEach((item) => {
				if (!this.shopGroupsOrder[item.idShop]) {
					this.shopGroupsOrder[item.idShop] = [];
				}
				this.shopGroupsOrder[item.idShop].push(item);
			});
		},

		groupByShopId() {
			this.shopGroups = {};
			this.items.forEach((item) => {
				if (!this.shopGroups[item.idShop]) {
					this.shopGroups[item.idShop] = [];
				}
				this.shopGroups[item.idShop].push(item);

			});


			// Sắp xếp các sản phẩm trong từng cụm theo CreatedDay giảm dần
			for (const shopId in this.shopGroups) {
				if (this.shopGroups.hasOwnProperty(shopId)) {
					this.shopGroups[shopId].sort((a, b) => {
						// Sử dụng Unix timestamp để so sánh ngày
						const dateA = new Date(a.createdDay).getTime();
						const dateB = new Date(b.createdDay).getTime();
						return dateB - dateA; // Sắp xếp giảm dần
					});
				}
			}
			// Chuyển đối tượng thành mảng các cặp key-value
			const objArray = Object.entries(this.shopGroups);
			// Sắp xếp mảng objArray dựa trên trường createdDay giảm dần
			objArray.sort((a, b) => {
				const dateA = new Date(a[1][0].createdDay).getTime();
				const dateB = new Date(b[1][0].createdDay).getTime();
				return dateB - dateA;
			});
			// Chuyển lại thành đối tượng từ mảng objArray đã sắp xếp
			this.shopGroups = Object.fromEntries(objArray);

		},
	});

	$cart.loadProductCart();
	$cart.loadVoucher();
	$cart.loadVoucherOfUser();
	// Đặt hàng
	$scope.message = "";

	$scope.checkAndPerformAction = function() {
		var userAddress = document.getElementById("userAddress").value;
		var userPhoneAddress = document.getElementById("userPhoneAddress").value;
		if ($cart.itemsOrder.length === 0) {
			this.message = "Vui lòng chọn sản phẩm muốn mua!";
			$('#ModalOrderMessage').modal('show');

		} else if (userAddress === "" || userPhoneAddress === "") {
			this.message = "Vui lòng cung cấp thông tin địa chỉ của bạn!";
			$('#ModalOrderMessage').modal('show');
		} else {
			sessionStorage.setItem('itemsOrder', JSON.stringify($cart.itemsOrder));
			// Sử dụng $location để chuyển hướng đến URL '/detail-order'
			$window.location.href = '/cart/detail-order';
		}
	};

	$scope.payment = "cash";

	//	$scope.checkPayment = function() {
	//		var payment = document.getElementsByName("pay").value;
	//		if(payment === "paypal") {
	//			$scope.payment = "paypal";
	//		}else if(payment === "vnpay") {
	//			$scope.payment = "vnpay";
	//		}else {
	//			$scope.payment = "cash";
	//		}
	//	}

	$scope.purchase = function() {
		// Thực hiện đặt hàng
		const requestDataDTO = {
			itemsOrderSession: $cart.itemsOrderSession,
			items: $cart.voucherApply
		};
		var url = `${host}/cart/order`;
		$http
			.post(url, requestDataDTO)
			.then((resp) => {
				//				alert("Đặt hàng thành công!");
				this.message = "Đặt hàng thành công!";
				$('#ModalOrderMessage').modal('show');
				console.log(resp);
				$cart.selectAll = true;
				$cart.toggleSelectAll();
				$cart.loadProductCart();
//				sessionStorage.removeItem('itemsOrder');
				var form = document.createElement("form");
	            form.method = "POST";
				console.log($scope.payment);
	            if ($scope.payment === "paypal") {
	                form.action = "/paypal-payment"; // Thay thế bằng URL tương ứng
	            } else if ($scope.payment === "vnpay") {
	                form.action = "/submitOrder"; // Thay thế bằng URL tương ứng
	            } else {
	                form.action = "/cash-payment"; // Thay thế bằng URL tương ứng
	            }
	            // Thêm form vào trang web và gửi POST request
         	    document.body.appendChild(form);
        	    form.submit();
			})
			.catch((error) => {
				this.message = "Lỗi khi đặt hàng!";
				$('#ModalOrderMessage').modal('show');
				console.log(error);
			});
	};

});
