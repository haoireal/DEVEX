/** @format */

var app = angular.module("myapp", ["ngRoute"]);
app.config(function ($routeProvider) {
  $routeProvider
    .when("/homepage", { templateUrl: "homepage.html", controller: "myCtrl" })
    .when("/menshoes", {
      templateUrl: "Men-shoes.html?" + Math.random(),
      controller: "MenshoesCtrl",
    })
    .when("/womenshoes", {
      templateUrl: "Women-sshoes.html?" + Math.random(),
      controller: "WomenshoesCtrl",
    })
    .when("/kidshoes", {
      templateUrl: "Kid.shoes.html?" + Math.random(),
      controller: "KidshoesCtrl",
    })
    .when("/productdetail/:productId", {
      templateUrl: "productsdetail.html?" + Math.random(),
      controller: "ProductDetailCtrl",
    })
    .when("/cart", {
      templateUrl: "cart.html?" + Math.random(),
      controller: "CartlCtrl",
    })
    .otherwise({ templateUrl: "homepage.html" });
});
//?TÌM KIẾM
app.service("searchService", function () {
  $scope.products = [];
  $http.get("Data/men-products.json").then(function (reponse) {
    $scope.products = $scope.products.concat(reponse.data);
  });
  $http.get("Data/women-products.json").then(function (reponse) {
    $scope.products = $scope.products.concat(reponse.data);

  });

  this.getProducts = function () {
    return products;
  };

  this.searchProducts = function (query) {
    var results = [];
    for (var i = 0; i < products.length; i++) {
      if (products[i].name.indexOf(query) !== -1) {
        results.push(products[i]);
      }
    }
    return results;
  };
});
app.controller('SearchController', function($scope, $location) {
  $scope.search = function() {
    $location.path('/products').search({q: $scope.query});
  };
});


//?END TÌM KIẾM
app.controller("myCtrl", function ($scope, $rootScope, $routeParams, $http) {});
//! Men-Shoes
app.controller(
  "MenshoesCtrl",
  function ($scope, $rootScope, $routeParams, $http) {
    $scope.products = [];
    $http.get("Data/men-products.json").then(function (reponse) {
      console.log(1);
      $scope.products = reponse.data;
    });
    $scope.visible = 15;
    $scope.showMore = function () {
      $scope.visible += 6;
    };

    
  const products = document.querySelector("#products");
    const aside = document.querySelector("#aside");
    $scope.showAside = true;
    $scope.toggleAside = function () {
      $scope.showAside = !$scope.showAside;
    };
    $scope.search = '';
  }
);
//! Women-Shoes
app.controller(
  "WomenshoesCtrl",
  function ($scope, $rootScope, $routeParams, $http) {
    $scope.products = [];
    $http.get("Data/women-products.json").then(function (reponse) {
      console.log(1);
      $scope.products = reponse.data;
    });
  }
);

//!PRODUCT DETAIL CONTROL
app.controller(
  "ProductDetailCtrl",
  function ($scope, $rootScope, $routeParams, $http) {
    $scope.products = [];
    $http.get("Data/men-products.json").then(function (reponse) {
      $scope.products = $scope.products.concat(reponse.data);
    });
    $http.get("Data/women-products.json").then(function (reponse) {
      $scope.products = $scope.products.concat(reponse.data);

      $scope.product = $scope.products
        .filter(function (product) {
          return product.id === $routeParams.productId.substring(1);
        })
        .find(function (product) {
          return true; // tìm thấy bất kỳ sản phẩm nào trong mảng con
        });
      console.log($routeParams.productId.substring(1));
      console.log($scope.product);
    });
    $scope.addToCart = function (product) {
      if (typeof $rootScope.cart == "undefined") {
        $rootScope.cart = [];
      }

      // Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
      var index = -1;
      for (var i = 0; i < $rootScope.cart.length; i++) {
        if ($rootScope.cart[i].id == product.id) {
          index = i;
          break;
        }
      }
      // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng sản phẩm đó lên
      if (index != -1) {
        $rootScope.cart[$rootScope.cart.index - 1].quantity++;
      }
      // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm sản phẩm đó vào giỏ hàng
      else {
        product.quantity = 1;

        $rootScope.cart.push(product);
        $rootScope.cart.index = $rootScope.cart.length;
        $rootScope.cart.quantity = product.quantity;
      }
      // Lưu giỏ hàng vào localStorage
      localStorage.setItem("cart", JSON.stringify($rootScope.cart));
      console.log($rootScope.cart);
    };
  }
);
//!SIZE-PRODUCT DETAIL CONTROL
/*app.controller("SizeController", function ($scope) {
  $scope.sizes = [
    "S",
    "M",
    "L",
  ];
});*/

//!CART
app.controller("CartlCtrl", function ($scope, $rootScope, $routeParams, $http) {
  
  $scope.sizes = [
    "S",
    "M",
    "L",
  ];
  // Quantity data
  $scope.quantities = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  console.log($rootScope.cart);
  $rootScope.sumMoney = 0;
  if (typeof $rootScope.cart != "undefined") {
    
    for (var i = 0; i < $rootScope.cart.length; i++) {
      // Lấy thẻ select
      var select = $("#quantitySelect");
     
      

    }
  }
  if (typeof $rootScope.cart != "undefined") {
    //Có giỏ hàng mới tính tổng
    for (var i = 0; i < $rootScope.cart.length; i++) {
      $rootScope.sumMoney =
        $rootScope.sumMoney +
        $rootScope.cart[i].quantity * $rootScope.cart[i].price;
    }
  }
  
  //Xóa sản phẩm trong giỏ hàng
  $scope.delProduct = function (index) {
    $rootScope.cart.splice(index - 1, 1);
    if (typeof $rootScope.cart != "undefined") {
      $rootScope.sumMoney = 0;
      for (var i = 0; i < $rootScope.cart.length; i++) {
        $rootScope.sumMoney =
          $rootScope.sumMoney +
          $rootScope.cart[i].quantity * $rootScope.cart[i].price;
      }
    }
  };

  //!SETQUANTITY
  

  
});
