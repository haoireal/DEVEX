package com.Devex.Controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;

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
	ProductRepository productRepository;

	@GetMapping("/home")
	public String getHomePage() {

		return "seller/index";
	}

	@GetMapping("/list/{listName}")
	public String getAnyList(@PathVariable("listName") String listName, Model model) {
		switch (listName) {
		case "products": {
			model.addAttribute("titleType", "Sản phẩm");
			List<Product> listProducts = productRepository.findAll();
//			for (Product product : listProducts) {
//				System.out.println(product);
//			}
			model.addAttribute("products", listProducts);
			break;
		}
		case "orders": {
			model.addAttribute("titleType", "Đơn hàng");
			// câu lệnh select seller ở đây
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
	
	@GetMapping("/profile")
	public String getSellerProfile() {

		return "seller/sellerProfile";
	}
	@GetMapping("/orderDetail")
	public String getOrderDetail(Model model) {
		model.addAttribute("shopName", "HÀO ĐẸP TRAI");
		return "seller/order/orderDetail";
	}
	@GetMapping("/orderPrint")
	public String getOrderPrint(Model model) {
		model.addAttribute("shopName", "HÀO ĐẸP TRAI");
		return "seller/order/orderPrint";
	}
	@GetMapping("/orderReport")
	public String getOrderReport(Model model) {
		return "seller/order/orderReport";
	}
}
