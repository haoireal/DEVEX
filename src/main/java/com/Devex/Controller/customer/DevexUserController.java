package com.Devex.Controller.customer;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.Devex.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.Devex.Sevice.UserSearchService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

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
	UserService userService;

	@Autowired
	UserRoleService userRoleService;
	
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

	@Autowired
	UserSearchService userSearchService;

	private List<Product> uniqueProductList = new ArrayList<>();
	private List<String> listCategory = new ArrayList<>();
	private List<String> listBrand = new ArrayList<>();
	private List<Product> temPoraryList = new ArrayList<>();

	@GetMapping({ "/home", "/*" })
	public String getHomePage(Model model, Principal principal) throws Exception {
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
			List<FlashSaleTime> flashSaleTimePast = flashSalesTimeService.findFlashSaleTimesByTimePast();
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
		if (idFlashSaleTimePast != null) {
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
		// Collections.shuffle(uniqueProductList);
		List<Category> listCategoryProducts = categoryService.findAll();

		model.addAttribute("listProductFlashSale", listProductFlashSaleNow);
		model.addAttribute("category", listCategoryProducts);
		model.addAttribute("products", uniqueProductList);
		
		//check quyền admin?
		User userAdmin = null;
		if(principal != null) {
			String id = principal.getName();
			if(id != null) {
				userAdmin = userService.findById(id).orElse(null);	
			}
		}
		boolean adminFlag = false;
		if(userAdmin != null) {
			List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
			for (UserRole u : roles) {
				if (u.getRole().getId().equals("ADMIN")) {
					System.out.println("tôi là admin");
					adminFlag = true;
				}
			}
		}
		model.addAttribute("admin", adminFlag);
		return "user/index";
	}

	@GetMapping("/userProfile")
	public String getUserProfile() {

		return "admin/userManage/userProfile";
	}

	@GetMapping("/product/search")
	public String searchProduct(Model model, @RequestParam("search") Optional<String> kw) {
		List<UserSearch> historySearch = userSearchService.findAll();
		Set<String> listHistorySearch = new LinkedHashSet<>();
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		String cleanKeywords = removeSpecialCharacters(kwords); // loại bỏ kí tự đặc biệt
		sessionService.set("keywordsSearch", cleanKeywords);
		if (listHistorySearch.size() <= 0) {
			userSearchService.insertKeyWorks(cleanKeywords);
		}
		// tách các keyWord
		historySearch.forEach(key -> {
			listHistorySearch.add(cleanKeywords);
		});
		listHistorySearch.forEach(keyW -> {
			if (!listHistorySearch.contains(cleanKeywords)) {
				userSearchService.insertKeyWorks(cleanKeywords);
			}
		});

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

	// @GetMapping("/sol")
	// public String getTestSOL() {

	// return "user/tetsSol";
	// }
	public String removeSpecialCharacters(String input) {
		// Biểu thức chính quy để giữ lại chỉ các ký tự chữ cái, khoảng trắng, số và _
		String regex = "[^\\p{L}\\p{N}\\s]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.replaceAll("");
	}
}// end class
