package com.Devex.Controller.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RecommendationSystem;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ShoppingCartService;

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

	@Autowired
	ShoppingCartService cartService;

	@GetMapping("/home")
	public String getHomePage(Model model) {
		User user = new User();
		List<Product> listProducts = new ArrayList<>();
		user = sessionService.get("user");
		// Giỏ hàng
		if (sessionService.get("user") != null) {
			
			listProducts.addAll(recomendationService.recomendProduct(user.getUsername()));
			// fix tạm
			if (listProducts.size() <= 0) {
				listProducts.addAll(recomendationService.recomendProduct("baolh"));
			}
			// end fix tạm
		} else {
			// fix tạm
			listProducts.addAll(recomendationService.recomendProduct("baolh"));
			// end fix tạm
			sessionService.set("cartCount", 0);
		}
		// Trộn ví trí sản phẩm
		Collections.shuffle(listProducts);
		listProducts = listProducts.subList(0, 30);
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
		// Tìm tên từ theo từ khóa
		list.addAll(productService.findByKeywordName(kwords));
		System.out.println("chan loi");
		// Tìm theo shop bán
		list.addAll(productService.findProductBySellerUsername("%" + kwords + "%"));
		// FILLTER SẢN PHẨM TRÙNG NHAU
		List<Product> pByC = new ArrayList<>();
		if (list.size() > 0) {
			pByC = productService.findProductsByCategoryId(list.get(0).getCategoryDetails().getCategory().getId());
		}
		pByC.removeAll(list);
		// FIll thêm sản phẩm theo loại dựa trên từ khóa tìm kiếm
		list.addAll(pByC);
		List<Product> listFillter = productRepository.fillerProductBy(kwords, "price", "desc");

		model.addAttribute("products", list);
		return "user/findproduct";
	}

	@RequestMapping("/filter/price")
	public String filterPrice(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		String sortPrice = paramService.getString("sortprice", "");
		List<Product> listFillter = new ArrayList<>();
		System.out.println(sortPrice);
		switch (sortPrice) {
		case "ASC": {
			listFillter.addAll( productRepository.fillerProductBy(kwords, "price", "ASC"));
			break;
		}
		case "DESC": {
			listFillter.addAll( productRepository.fillerProductBy(kwords, "price", "DESC"));
			break;
		}
		default:
			
		}
		// Tìm tên từ theo từ khóa
		model.addAttribute("products", listFillter);
		return "user/findproduct";
	}
	
	@PostMapping("/filter/ban-chay")
    public String filterProducts(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		List<Product> listFillter = new ArrayList<>();
		System.out.println(listFillter.size());
		listFillter.addAll( productRepository.fillerProductBy(kwords, "soldcount", "DESC"));
		// Tìm tên từ theo từ khóa
				model.addAttribute("products", listFillter);
        return "user/findproduct";
    }
}
