package com.Devex.Controller.seller;

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
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class DevexSellerController {

	@Autowired
	SessionService session;

	@Autowired
	CookieService cookie;

	@Autowired
	ParamService param;
	
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

	@GetMapping("/home")
	public String getHomePage() {

		return "seller/index";
	}

	@GetMapping("/list/{listName}")
	public String getAnyList(@PathVariable("listName") String listName, Model model) {
		User u = session.get("user");
		switch (listName) {
		case "products": {
			model.addAttribute("titleType", "Sản phẩm");
			List<Product> listProducts = productService.findProductBySellerUsernameAndIsdeleteProduct("aligqd911");
//			for (Product product : listProducts) {
//				System.out.println(product);
//			}
			model.addAttribute("products", listProducts);
			break;
		}
		case "orders": {
			model.addAttribute("titleType", "Đơn hàng");
			List<Order> listOrder = orderService.findOrdersBySellerUsername("aligqd911");
			model.addAttribute("orders", listOrder);
			break;
		}
		case "rating": {
			model.addAttribute("titleType", "Đánh giá");
			// câu lệnh select seller ở đây
			break;
		}
		default:

		}
		model.addAttribute("listName", listName);
		return "seller/listManage";
	}
	
	@GetMapping("/product/show")
	public String showProduct() {
		
		return "seller/formManage";
	}
	
	@GetMapping("/product/edit/{idproduct}")
	public String editProduct(@PathVariable("idproduct") String idproduct, Model model) {
		Product product = productService.findByIdProduct(idproduct);
		List<Category> listCategory = categoryService.findAll();
		Category ca = categoryService.findByProductId(idproduct);
		List<CategoryDetails> listCategoryDetail = categoryDetailService.findAllCategoryDetailsById(ca.getId());
		model.addAttribute("product", product);
		model.addAttribute("categorys", listCategory); 
		model.addAttribute("categorydt", listCategoryDetail); 
		String id = product.getId();
		session.set("idproduct", id);
		System.out.println(id);
		return "seller/formManage";
	}
	
	@GetMapping("/order/xacnhan")
	public String xacNhanDonHang(@RequestParam("id") String id) {
		orderService.updateIdOrderStatus(1002, id);
		return "redirect:/seller/orderDetail/" + id;
	}
	
	@GetMapping("/order/hoanthanh")
	public String hoanthanh(@RequestParam("id") String id) {
		orderService.updateIdOrderStatus(1006, id);
		return "redirect:/seller/orderDetail/" + id;
	}
	
	@GetMapping("/profile")
	public String getSellerProfile() {

		return "seller/sellerProfile";
	}
	
	@GetMapping("/orderDetail/{id}")
	public String getOrderDetail(@PathVariable("id") String id, Model model) {
		List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderIDAndSellerUsername(id, "aligqd911");
		model.addAttribute("orderDetails", listOrderDetails);
		model.addAttribute("idPrint", id);
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		if (order.getOrderStatus() != null && order.getOrderStatus().getName().equalsIgnoreCase("Hoàn thành")) {
		    model.addAttribute("check", false);
		} else {
		    model.addAttribute("check", true);
		}
		return "seller/order/orderDetail";
	}
	@GetMapping("/orderPrint")
	public String getOrderPrint(Model model, @RequestParam("id") String id) {
		List<OrderDetails> listOrderDetails = detailService.findOrderDetailsByOrderIDAndSellerUsername(id, "aligqd911");
		model.addAttribute("orderDetails", listOrderDetails);
		model.addAttribute("idPrint", id);
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		if (order.getOrderStatus() != null && order.getOrderStatus().getName().equalsIgnoreCase("Hoàn thành")) {
		    model.addAttribute("check", false);
		} else {
		    model.addAttribute("check", true);
		}
		return "seller/order/orderPrint";
	}
	@GetMapping("/orderReport")
	public String getOrderReport(Model model) {
		return "seller/order/orderReport";
	}
}
