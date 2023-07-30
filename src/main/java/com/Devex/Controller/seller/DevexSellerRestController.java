package com.Devex.Controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SessionService;
import com.Devex.Utils.FileManagerService;

import jakarta.websocket.server.PathParam;
@CrossOrigin("*")
@RestController
public class DevexSellerRestController {

	@Autowired
	FileManagerService fileManagerService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryDetailService categoryDetailService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductVariantService productVariantService;
	
	@GetMapping("/img/product")
    public List<String> listImage() {
        // Kích hoạt FileManagerService
		String id = session.get("idproduct");
        List<String> imageUrls = fileManagerService.list(id, "aligqd911");
        return imageUrls;
    }
	
	@GetMapping("/img/product/{filename}")
	public byte[] download(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		return fileManagerService.read("aligqd911", id, filename);
	}
	
	@DeleteMapping("/img/product/{filename}")
	public void delete(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		fileManagerService.delete("aligqd911", id, filename);
	}
	
	@PostMapping("/img/product")
	public List<String> upload(@PathParam("files") MultipartFile[] files) {
		String id = session.get("idproduct");
		return fileManagerService.save("aligqd911", id, files);
	}
	
	@GetMapping("/api/product")
	public Product getProduct() {
		String id = session.get("idproduct");
		System.out.println(id);
		return productService.findByIdProduct(id);
	}
	
	@GetMapping("/categoryDetails/{idca}")
    public List<CategoryDetails> listCategoryDetails(@PathVariable("idca") int idca) {
        // Kích hoạt FileManagerService
		int id = idca;
		System.out.println(id);
        List<CategoryDetails> listcategory = categoryDetailService.findAllCategoryDetailsById(id);
        return listcategory;
    }
	
	@GetMapping("/idca")
	public int idCategory() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getCategory().getId();
		return id;
	}
	
	@GetMapping("/idcadt")
	public List<Category> idCategoryDetails() {
		List<Category> listCategory = categoryService.findAll();
		return listCategory;
	}
	
	@GetMapping("/api/productvariant")
	public List<ProductVariant> getProductVariant(){
		System.out.println("aaaaaaaaaaaaaaaaaaa");
		String idp = session.get("idproduct");
		List<ProductVariant> listproductvariant = productVariantService.findAllProductVariantByProductId(idp);
		return listproductvariant;
	}
	
	@PutMapping("/info/product")
	public Product updateProduct(@RequestBody Product product) {
		System.out.println(product.getId());
		return productService.save(product);
	}
	
}
