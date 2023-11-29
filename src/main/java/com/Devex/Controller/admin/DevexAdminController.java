package com.Devex.Controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Devex.DTO.FlashSaleTimeDTO;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FlashSalesTimeService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import io.jsonwebtoken.lang.Collections;

@Controller
@RequestMapping("/ad")
public class DevexAdminController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private CookieService cookie;

	@Autowired
	private ParamService param;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FlashSalesTimeService flashSalesTimeService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/home")
	public String getHomePage() {
		sessionService.set("request", false);
		return "admin/index";
	}

	@GetMapping("/list/{listName}")
	public String getAnyList(@PathVariable("listName") String listName, Model model) {
		switch (listName) {
		case "user": {
			model.addAttribute("titleType", "Người dùng");
			//câu lệnh select user ở đây
			break;
		}
		case "seller": {
			model.addAttribute("titleType", "Nhà phân phối");
			// câu lệnh select seller ở đây
			break;
		}
		default:

		}
		model.addAttribute("listName", listName);
		return "admin/listManage";
	}

	@GetMapping("/userProfile")
	public String getUserProfile() {

		return "admin/userManage/userProfile";
	}
	
	@GetMapping("/flashSales")
	public String getFlashSales(ModelMap model) {
		
		
		return "admin/FlashSalesManage";
	}
	
	@GetMapping("/categorymanage")
	public String getCategoryManage() {
		
		return "admin/productManage/formCategoryManage";
	}
	
	@GetMapping("/productmanage")
	public String getProductManage() {
		
		return "admin/productManage/listProduct";
	}
	
	@GetMapping("/requestproduct")
	public String getRequestProduct() {
		
		return "admin/productManage/requestProduct";
	}
	
	@GetMapping("/showproduct/{id}")
	public String showInfoProduct(@PathVariable("id") String id, Model model) {
		Product p = productService.findByIdProduct(id);
		model.addAttribute("username", p.getSellerProduct().getUsername());
		model.addAttribute("idproduct", id);
		sessionService.set("usernameseller", p.getSellerProduct().getUsername());
		sessionService.set("idproduct", id);
		return "admin/productManage/formProduct";
	}
	
	@GetMapping("/list/distributor")
	public String showDistributor() {
		return "admin/sellerManage/sellerManage";
	}

}
