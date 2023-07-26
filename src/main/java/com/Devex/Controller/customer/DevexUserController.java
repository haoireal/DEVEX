package com.Devex.Controller.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
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
	
	@Autowired
	ProductRepository productRepository;
	

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
		List<Product> list = new ArrayList<>();
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		//Tìm tên từ theo từ khóa
		list.addAll(productService.findByKeywordName(kwords));
		//Tìm theo shop bán
		list.addAll(productService.findProductBySellerUsername("%"+kwords+"%"));
		//FILLTER SẢN PHẨM TRÙNG NHAU
		List<Product> pByC = new ArrayList<>();
		if(list.size() > 0) {
			pByC = productService.findProductsByCategoryId(list.get(0).getCategoryDetails().getCategory().getId());
		}
		pByC.removeAll(list);
		
		
		//FIll thêm sản phẩm theo loại dựa trên từ khóa tìm kiếm
		list.addAll(pByC);
		List<Product> listFillter =productRepository.fillerProductBy(kwords, "price", "desc");
				
		model.addAttribute("products", list);
		return "user/findproduct";
	}
}
