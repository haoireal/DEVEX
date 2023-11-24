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
		int idFlashSaleTimeFuture = 0;
		try {
			FlashSaleTime flashSaleTime = flashSalesTimeService.findFlashSaleTimesByTimeNow();
			FlashSaleTime flashSaleTimeFuture = flashSalesTimeService.findFlashSaleTimesByTimeFuture();
			if (flashSaleTime != null) {
				idFlashSaleTimeNow = flashSaleTime.getId();

			}

			if (flashSaleTimeFuture != null) {
				idFlashSaleTimeFuture = flashSaleTimeFuture.getId();
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);

		}

		List<Product> listProductFlashSaleNow = productService.findProductsByFlashSaleTimeAndStatus(idFlashSaleTimeNow,
				true);
		List<Product> listProductFlashSaleFuture = productService
				.findProductsByFlashSaleTimeAndStatus(idFlashSaleTimeFuture, true);
//		listProductFlashSaleFuture.forEach( test -> {
//			System.out.println("sssNAME: " + test.getName());
//			System.out.println("sssNAME: " + test.getProductVariants().get(0).getPriceSale());
//		});
//		System.out.println("---------------------------------------------");
//		listProductFlashSaleNow.forEach( test -> {
//			System.out.println("sssNAME: " + test.getName());
//			System.out.println("sssNAME: " + test.getProductVariants().get(0).getPriceSale());
//		});
		if (idFlashSaleTimeNow != 0) {
			listProductFlashSaleNow.forEach(pLN -> {
				System.out.println("ssss" + pLN.getProductVariants().get(0).getListFlashSale().get(0).getDiscount());
				productVariantService.updatePriceSale(
						pLN.getProductVariants().get(0).getListFlashSale().get(0).getDiscount(),
						pLN.getProductVariants().get(0).getId());
			});
		} else {
			System.out.println("ngu chưa");
			listProductFlashSaleNow.forEach(pLN -> {
				productVariantService.updatePriceSale(pLN.getProductVariants().get(0).getPrice(),
						pLN.getProductVariants().get(0).getId());
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
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);

		return "user/findproduct";
	}

	@RequestMapping("/filter/price")
	public String filterPrice(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		String sortPrice = paramService.getString("sortprice", "");
		switch (sortPrice) {
		case "ASC": {
			Collections.sort(temPoraryList, new Comparator<Product>() {
				@Override
				public int compare(Product product1, Product product2) {
					// Lựa chọn giá thấp nhất hoặc giá cao nhất để sắp xếp
					Double price1 = product1.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).min()
							.orElse(0.0);
					Double price2 = product2.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).min()
							.orElse(0.0);

					return Double.compare(price1, price2);
				}
			});
			break;
		}
		case "DESC": {
			Collections.sort(temPoraryList, new Comparator<Product>() {
				@Override
				public int compare(Product product1, Product product2) {
					// Lựa chọn giá thấp nhất hoặc giá cao nhất để sắp xếp
					Double price1 = product1.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).min()
							.orElse(0.0);
					Double price2 = product2.getProductVariants().stream().mapToDouble(ProductVariant::getPrice).min()
							.orElse(0.0);

					return Double.compare(price2, price1);
				}
			});
			break;
		}
		default:
			model.addAttribute("products", temPoraryList);
		}
		// Tìm tên từ theo từ khóa
		model.addAttribute("products", temPoraryList);
		model.addAttribute("category", listCategory);
		model.addAttribute("brand", listBrand);
		return "user/findproduct";
	}

	@PostMapping("/filter/ban-chay")
	public String filterProducts(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		Collections.sort(temPoraryList, Comparator.comparing(Product::getSoldCount).reversed());
		// Tìm tên từ theo từ khóa
		model.addAttribute("products", temPoraryList);
		model.addAttribute("category", listCategory);
		model.addAttribute("brand", listBrand);
		return "user/findproduct";
	}

	@RequestMapping("/category/{id}")
	public String filterCategory(@PathVariable("id") int id, ModelMap model) {
		uniqueProductList = productService.findProductsByCategoryId(id);
		sessionService.set("keywordsSearch", uniqueProductList.get(0).getCategoryDetails().getCategory().getName());
		getCategoryAndBrand();
		temPoraryList = uniqueProductList;
		model.addAttribute("products", uniqueProductList);
		model.addAttribute("category", listCategory);
		model.addAttribute("brand", listBrand);
		return "user/findproduct";
	}

	@PostMapping("/filter-product")
	public String filterProducts(@RequestParam(value = "category", required = false) List<String> category,
			@RequestParam(value = "location", required = false) List<String> location,
			@RequestParam(value = "brand", required = false) List<String> brand,
			@RequestParam(value = "priceForm", required = false) Double priceFrom,
			@RequestParam(value = "priceTo", required = false) Double priceTo, ModelMap model) {
		// Thực hiện việc lọc danh sách sản phẩm ở đây dựa trên các điều kiện đã nhận
		// được từ giao diện web
		temPoraryList = uniqueProductList.stream().filter(pr -> {
			// Áp dụng các điều kiện lọc ở đây
			boolean categoryCondition = category == null || category.isEmpty()
					|| category.contains(pr.getCategoryDetails().getCategory().getName());
			boolean brandCondition = brand == null || brand.isEmpty() || brand.contains(pr.getProductbrand().getName());
			boolean locationCondition = location == null || location.isEmpty()
					|| location.contains(pr.getSellerProduct().getAddress());
			boolean priceCondition = (priceFrom == null || pr.getProductVariants().get(0).getPriceSale() >= priceFrom)
					&& (priceTo == null || pr.getProductVariants().get(0).getPriceSale() <= priceTo);
			return categoryCondition && locationCondition && brandCondition && priceCondition;
		}).collect(Collectors.toList());

		// Trả về danh sách sản phẩm đã lọc cho giao diện web
		model.addAttribute("products", temPoraryList);
		model.addAttribute("category", listCategory);
		model.addAttribute("brand", listBrand);
		return "user/findproduct"; // Trả về trang kết quả
	}

	// function
	void getCategoryAndBrand() {
		uniqueProductList.forEach(category -> {

			if (!listCategory.contains(category.getCategoryDetails().getCategory().getName())) {
				listCategory.add(category.getCategoryDetails().getCategory().getName());
			}

			if (!listBrand.contains(category.getProductbrand().getName())) {
				listBrand.add(category.getProductbrand().getName());
			}

		});

	}
	
	@GetMapping("/pageseller/{username}")
	public String showPageSeller(@PathVariable("username") String username, ModelMap model) {
		sessionService.set("userSeller", username);
		model.addAttribute("username", username);
		return "user/sellerPage";
	}
}// end class
