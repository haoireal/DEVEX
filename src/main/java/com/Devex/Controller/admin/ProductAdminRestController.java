package com.Devex.Controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductBrand;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.ProductBrandService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductAdminRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private CategoryDetailService categoryDetailService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private NotiService notiService;
	
	@Autowired
	private ProductBrandService brandService;
	
	@GetMapping("/getcategory")
	public List<Category> getAllCategory(){
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@GetMapping("/getcategorydetails")
	public List<CategoryDetails> getAllCategoryDetailsByCategoryId(@RequestParam("id") int id){
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(id);
	}
	
	@PostMapping("/add/category")
	public Map<String, Object> insertCategory(@RequestParam("name") String name){
		categoryService.insertCategory(name);
		Map<String, Object> mapAllCategory = new HashMap<>();
		Category c = categoryService.getCategoryNew();
		List<Category> listCategory = categoryService.findAllCategoryNotNameLikeUnknown();
		mapAllCategory.put("id", c.getId());
		mapAllCategory.put("listCategory", listCategory);
		return mapAllCategory;
	}
	
	@PostMapping("/add/categorydetails")
	public List<CategoryDetails> insertCategoryDetails(@RequestParam("name") String name, @RequestParam("id") int id){
		categoryDetailService.insertCategoryDetails(name, id);
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(id);
	}
	
	@DeleteMapping("/delete/category")
	public List<Category> deleteCategory(@RequestParam("id") int id){
		categoryService.deleteById(id);
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@DeleteMapping("/delete/categorydetails")
	public List<CategoryDetails> deleteCategoryDetails(@RequestParam("iddelete") int iddelete, @RequestParam("idfill") int idfill){
		categoryDetailService.deleteById(iddelete);
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(idfill);
	}
	
	@PutMapping("/update/categorydetails")
	public List<CategoryDetails> updateCategoryDetails(@RequestParam("id") int id, @RequestParam("idfill") int idfill){
		List<Product> listProduct = productService.findProductsByCategoryDetailsId(id);
		boolean check = false;
		for (Product p : listProduct) {
			productService.updateProductCategoryByIdCategory(141, p.getId());
			check = true;
		}
		if(check == true) {
			categoryDetailService.deleteById(id);
		}
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(idfill);
	}
	
	@PutMapping("/update/category")
	public List<Category> updateCategory(@RequestParam("id") int id){
		List<CategoryDetails> listCategoryDetails = categoryDetailService.findAllCategoryDetailsById(id);
		for (CategoryDetails cad : listCategoryDetails) {
			List<Product> listProduct = productService.findProductsByCategoryDetailsId(cad.getId());
			for (Product p : listProduct) {
				productService.updateProductCategoryByIdCategory(141, p.getId());
			}
			categoryDetailService.deleteById(cad.getId());
		}
		categoryService.deleteById(id);
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@GetMapping("/getamountproduct")
	public int getCountProductByCategoryDetailsId(@RequestParam("id") int id) {
		return productService.getCountProductByCategoryId(id);
	}
	
	@GetMapping("getamountcategorydetails")
	public int getCountCategoryDetailsByCategoryId(@RequestParam("id") int id) {
		return categoryDetailService.getCountCategoryDetailsByCategoryId(id);
	}
	
	@GetMapping("/getproductbrand")
	public List<ProductBrand> getAllProductBrand(){
		return brandService.getProductBrandNotUnknown();
	}
	
	@GetMapping("/getamountproductbrand")
	public int getCountProductByProductBrandId(@RequestParam("id") int id) {
		return productService.getCountProductByProductBrandId(id);
	}
	
	@DeleteMapping("/delete/productbrand")
	public List<ProductBrand> deleteProductBrand(@RequestParam("id") int id){
		brandService.deleteById(id);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PutMapping("/update/productbrand")
	public List<ProductBrand> updateProductBrand(@RequestParam("id") int id){
		List<Product> listProduct = productService.findAllProductByProductBrandId(id);
		for (Product p : listProduct) {
			productService.updateProductProductBrandByIdProductBrand(101, p.getId());
		}
		brandService.deleteById(id);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PostMapping("/add/productbrand")
	public List<ProductBrand> insertProductBrand(@RequestParam("name") String name){
		brandService.insertProductBrand(name);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PutMapping("/update/savecategory")
	public Map<String, Object> updateNameCategoryHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		categoryService.updateCategory(name, id);
		Map<String, Object> mapca = new HashMap<>();
		List<Category> listca = categoryService.findAllCategoryNotNameLikeUnknown();
		mapca.put("listca", listca);
		mapca.put("id", id);
		return mapca;
	}
	
	@PutMapping("/update/savecategorydetails")
	public int updateNameCategoryDetailsHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		categoryDetailService.updateCategoryDetails(name, id);
		CategoryDetails cad = categoryDetailService.findCategoryDetailsById(id);
		return cad.getCategory().getId();
	}
	
	@PutMapping("/update/saveproductbrand")
	public List<ProductBrand> updateNameProductBrandHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		brandService.updateProductBrand(name, id);
		return brandService.getProductBrandNotUnknown();
	}
}
