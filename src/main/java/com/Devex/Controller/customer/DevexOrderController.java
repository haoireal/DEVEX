package com.Devex.Controller.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Repository.OrderDetailRepository;
import com.Devex.Repository.OrderRepository;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Utils.FileManagerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DevexOrderController {

	@Autowired
	SessionService sessionService;

	@Autowired
	CookieService cookieService;

	@Autowired
	ParamService paramService;

	@Autowired
	ProductService productService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService detailService;

	@Autowired
	SellerService sellerService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryDetailService categoryDetailService;

	@Autowired
	FileManagerService fileManagerService;

	@Autowired
	ImageProductService imageProductService;

	@Autowired
	ProductVariantService productVariantService;

	@GetMapping("/order")
	public String getOrderPage(Model model) {
		User u = sessionService.get("user");
		List<Order> listOrder = orderService.findOrdersByCustomerID(u.getUsername());
		model.addAttribute("orders", listOrder);
		System.out.println("co " + listOrder.size() + " don hang");
		return "user/orderDetail";
	}

}
