app.controller("search-ctrl", function ($scope, $http, $window) {
  var $search = ($scope.search = {
    loadData: [],
    temLoadData: [],
    itemsPerPage: 25,
    currentPage: 1,
    totalPages: 1, // Khởi tạo totalPages ban đầu
    loadProductSearch: function () {
      $http
        .get("/api/search")
        .then((resp) => {
          this.loadData = resp.data;
          this.temLoadData = this.loadData.slice();
          this.totalPages = Math.ceil(this.loadData.length / this.itemsPerPage);
          this.updatePagedItems();
          console.log(this.loadData);
        })
        .catch(function (err) {
          console.error(err); // xử lý lỗi khi gọi API
        });
    }, // loading data

    firstPage: function () {
      this.currentPage = 1;
      this.updatePagedItems();
      $window.scrollTo(0, 0); // Cuộn lên đầu trang
    },

    prevPage: function () {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updatePagedItems();
        $window.scrollTo(0, 0); // Cuộn lên đầu trang
      }
    },

    nextPage: function () {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updatePagedItems();
        $window.scrollTo(0, 0); // Cuộn lên đầu trang
      }
    },
    lastPage: function () {
      this.currentPage = this.totalPages;
      this.updatePagedItems();
      $window.scrollTo(0, 0); // Cuộn lên đầu trang
    },

    updatePagedItems: function () {
      var begin = (this.currentPage - 1) * this.itemsPerPage;
      var end = Math.min(begin + this.itemsPerPage, this.loadData.length);
      this.pagedItems = this.loadData.slice(begin, end);
    },
    calculateDiscount: function (priceSale, price) {
      if (price && priceSale) {
        var discount = 100 - (priceSale / price) * 100;
        if (discount > 0) {
          return discount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ","); // Định dạng giá trị số với dấu phẩy ngăn cách hàng nghìn
        }
      }
      return "";
    },

    filterProductBySoldCount: function () {
      var selectedSoldCount = document.getElementById("danger-outlined").value;
      if (selectedSoldCount == "BAN_CHAY") {
        this.search.loadData.sort(function (a, b) {
          return b.soldCount - a.soldCount;
        });
      }
      console.log(selectedSoldCount);
    },
  }); // end function search

  $search.loadProductSearch();
});
