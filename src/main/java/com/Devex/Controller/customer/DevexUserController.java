package com.Devex.Controller.customer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.FlashSale;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FlashSalesTimeService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
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

	@Autowired
	CategoryService categoryService;

	@Autowired
	FlashSalesTimeService flashSalesTimeService;

	@Autowired
	ProductVariantService productVariantService;

	private List<Product> uniqueProductList = new ArrayList<>();
	private List<String> listCategory = new ArrayList<>();
	private List<String> listBrand = new ArrayList<>();
	private List<Product> temPoraryList = new ArrayList<>();

	@GetMapping({ "/home", "/*" })
	public String getHomePage(Model model) throws Exception {
//		uniqueProductList.clear();
		User user = new User();
		List<Product> listProducts = new ArrayList<>();
		Set<Product> uniqueProducts = new HashSet<>();
		user = sessionService.get("user");

		// Đối tượng LocalDate
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		// Chuyển LocalDate thành chuỗi
		int idFlashSaleTimeNow = 0;
		List<Integer> idFlashSaleTimePast = new ArrayList<>();
		try {
			FlashSaleTime flashSaleTime = flashSalesTimeService.findFlashSaleTimesByTimeNow();
			List<FlashSaleTime> flashSaleTimePast= flashSalesTimeService.findFlashSaleTimesByTimePast();
			if (flashSaleTime != null) {
				idFlashSaleTimeNow = flashSaleTime.getId();

			}

			if (flashSaleTimePast != null) {
				flashSaleTimePast.forEach(id -> {
					idFlashSaleTimePast.add(id.getId());
				});
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);

		}

		List<Product> listProductFlashSaleNow = productService.findProductsByFlashSaleTimeAndStatus(idFlashSaleTimeNow,
				true);
		List<Product> listProductFlashSalePast = new ArrayList<>();
		idFlashSaleTimePast.forEach(pr -> {
			listProductFlashSalePast.addAll(productService
			.findProductsByFlashSaleTimeAndStatus(pr, true));
		});

		if (idFlashSaleTimeNow != 0) {
			listProductFlashSaleNow.forEach(pLN -> {
				productVariantService.updatePriceSale(
						pLN.getProductVariants().get(0).getListFlashSale().get(0).getDiscount(),
						pLN.getProductVariants().get(0).getId());
			});
		} 
		if(idFlashSaleTimePast != null) {
			listProductFlashSalePast.forEach(pLP -> {
				productVariantService.updatePriceSale(pLP.getProductVariants().get(0).getPrice(),
						pLP.getProductVariants().get(0).getId());
			});
		}

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
		listProducts.forEach(pr -> {
			uniqueProducts.add(pr);
		});
		// Chuyển đổi lại thành danh sách (List)
		uniqueProductList = new ArrayList<>(uniqueProducts);
//		Collections.shuffle(uniqueProductList);
		List<Category> listCategoryProducts = categoryService.findAll();

		model.addAttribute("listProductFlashSale", listProductFlashSaleNow);
		model.addAttribute("category", listCategoryProducts);
		model.addAttribute("products", uniqueProductList);
		return "user/index";
	}

	@GetMapping("/userProfile")
	public String getUserProfile() {

		return "admin/userManage/userProfile";
	}

	@GetMapping("/product/search")
	public String searchProduct(Model model, @RequestParam("search") Optional<String> kw) {
		List<String> historySearch = new ArrayList<>();// thu thập từ khoá tìm kiếm của người dùng cần tạo bảng trong data
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		historySearch.add(kwords);
		
		return "user/findproduct";
	}

	
	

	@RequestMapping("/category/{id}")
	public String filterCategory(@PathVariable("id") int id, ModelMap model) {
		uniqueProductList = productService.findProductsByCategoryId(id);
		sessionService.set("keywordsSearch", uniqueProductList.get(0).getCategoryDetails().getCategory().getName());

		return "user/findproduct";
	}

	
	@GetMapping("/pageseller/{username}")
	public String showPageSeller(@PathVariable("username") String username, ModelMap model) {
		sessionService.set("userSeller", username);
		model.addAttribute("username", username);
		return "user/sellerPage";
	}
	
	@GetMapping("/sol")
	public String getTestSOL() {

		return "user/tetsSol";
	}
	
}// end class
