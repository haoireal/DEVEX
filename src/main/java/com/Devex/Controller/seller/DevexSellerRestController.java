package com.Devex.Controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.ProductService;
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
	
	@GetMapping("/img/product")
    public List<String> listImage() {
        // Kích hoạt FileManagerService
		String id = session.get("idproduct");
        List<String> imageUrls = fileManagerService.list(id, "khanhtq");
        return imageUrls;
    }
	
	@GetMapping("/img/product/{filename}")
	public byte[] download(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		return fileManagerService.read("khanhtq", id, filename);
	}
	
	@DeleteMapping("/img/product/{filename}")
	public void delete(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		fileManagerService.delete("khanhtq", id, filename);
	}
	
	@PostMapping("/img/product")
	public List<String> upload(@PathParam("files") MultipartFile[] files) {
		String id = session.get("idproduct");
		return fileManagerService.save("khanhtq", id, files);
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
	
}
