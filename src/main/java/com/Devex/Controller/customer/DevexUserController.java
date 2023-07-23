package com.Devex.Controller.customer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Product;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RecommendationSystem;
import com.Devex.Sevice.SessionService;

@Controller
public class DevexUserController {

	@Autowired
	SessionService sessionService;

	@Autowired
	CookieService cookieService;

	@Autowired
	ParamService paramService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	RecommendationSystem recomendationService;
	

	@GetMapping("/home")
	public String getHomePage(Model model) {
		List<Product> listProducts = recomendationService.recomendProduct("mbqeok970");
		//Trộn ví trí sản phẩm
		Collections.shuffle(listProducts);
		model.addAttribute("products", listProducts);
		return "user/index";
	}


	@GetMapping("/userProfile")
	public String getUserProfile() {

		return "admin/userManage/userProfile";
	}
	
	@GetMapping("/product/find")
	public String getPdDetail() {

		return "user/findproduct";
	}
	
	@GetMapping("/product/search")
	public String searchProduct(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		List<Product> list = productService.findByKeywordName("%" + kwords + "%");
		model.addAttribute("products", list);
		model.addAttribute("count", productService.countByKeywordName("%" + kwords + "%"));
		return "user/findproduct";
	}
}
