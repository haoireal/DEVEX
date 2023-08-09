package com.Devex.Controller.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.Devex.Entity.ImageProduct;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductBrand;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.ProductBrandService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Utils.FileManagerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	SellerService sellerService;

	@Autowired
	ImageProductService imageProductService;
	
	@Autowired
	ProductBrandService brandService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping("/img/product")
	public List<String> listImage() {
		// Kích hoạt FileManagerService
		User u = session.get("user");
		String id = session.get("idproduct");
		List<String> imageUrls = fileManagerService.list(id, u.getUsername());
		return imageUrls;
	}

	@GetMapping("/img/product/{filename}")
	public byte[] download(@PathVariable("filename") String filename) {
		User u = session.get("user");
		String id = session.get("idproduct");
		return fileManagerService.read(u.getUsername(), id, filename);
	}

	@DeleteMapping("/img/product/{filename}")
	public void delete(@PathVariable("filename") String filename) {
		User u = session.get("user");
		String id = session.get("idproduct");
		fileManagerService.delete(u.getUsername(), id, filename);
		imageProductService.deleteImageProductByNameAndProductId(filename, id);
	}

	@PostMapping("/img/product")
	public List<String> upload(@PathParam("files") MultipartFile[] files) {
		User u = session.get("user");
		String id = session.get("idproduct");
		return fileManagerService.save(u.getUsername(), id, files);
	}

	@GetMapping("/api/product")
	public Product getProduct() {
		String id = session.get("idproduct");
		return productService.findByIdProduct(id);
	}

	@GetMapping("/categoryDetails/{idca}")
	public List<CategoryDetails> listCategoryDetails(@PathVariable("idca") int idca) {
		// Kích hoạt FileManagerService
		int id = idca;
		List<CategoryDetails> listcategory = categoryDetailService.findAllCategoryDetailsById(id);
		return listcategory;
	}
	
	@GetMapping("/idCategoryDetails")
	public int idCategorydetails() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getId();
		return id;
	}

	@GetMapping("/idcategory")
	public int idCategory() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getCategory().getId();
		return id;
	}

	@GetMapping("/category")
	public List<Category> idCategoryDetails() {
		List<Category> listCategory = categoryService.findAll();
		return listCategory;
	}
	
	@GetMapping("/brand")
	public List<ProductBrand> listBrand(){
		return brandService.findAll();
	}
	
	@GetMapping("/idbrand")
	public int idBrand() {
		String idp = session.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getProductbrand().getId();
		return id;
	}

	@GetMapping("/api/productvariant")
	public List<ProductVariant> getProductVariant() {
		String idp = session.get("idproduct");
		List<ProductVariant> listproductvariant = productVariantService.findAllProductVariantByProductId(idp);
		return listproductvariant;
	}

	@SuppressWarnings("unused")
	@PutMapping("/info/product")
	public void updateProduct(@RequestBody Object object) throws ParseException {
		User u = session.get("user");
		// Chuyển object sang json sau đó đọc ra
		JsonNode jsonNode = objectMapper.valueToTree(object);
		JsonNode listNode = jsonNode.get("listvariant");
		List<ProductVariant> myList = objectMapper.convertValue(listNode, new TypeReference<List<ProductVariant>>() {
		});
		int idCategoryDetails = jsonNode.get("idCategoryDetails").asInt();
		Boolean active = jsonNode.get("active").asBoolean();
		int brand = jsonNode.get("brand").asInt();
		String createdDay = jsonNode.get("createdDay").asText();
		Date daycreated = dateFormat.parse(createdDay);
		String description = jsonNode.get("description").asText();
		String id = jsonNode.get("id").asText();
		String name = jsonNode.get("name").asText();
		CategoryDetails categoryDetails = categoryDetailService.findCategoryDetailsById(idCategoryDetails);
		Seller seller = sellerService.findFirstByUsername(u.getUsername());
		// Update product
		productService.updateProduct(id, name, brand, description, daycreated, active, seller.getUsername(),
				categoryDetails.getId());
		// Duyệt list productVariant để thực hiện insert hoặc update
		for (ProductVariant productVariant : myList) {
			Integer idProductVariant = productVariant.getId();
			if (idProductVariant >= 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.updateProductVariant(idProductVariant, productVariant.getQuantity(),
						productVariant.getPrice(), productVariant.getPriceSale(), productVariant.getSize(),
						productVariant.getColor());
			} else if (idProductVariant < 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.addProductVariant(productVariant.getQuantity(), productVariant.getPrice(),
						productVariant.getPriceSale(), productVariant.getSize(), productVariant.getColor(), id);
			}
		}
	}

	@DeleteMapping("/delete/product/{idproduct}")
	public void deleteproduct(@PathVariable("idproduct") String idproduct) {
		productService.updateProductIsDeleteById(true, idproduct);
	}
	
	@PostMapping("/addimageproduct/{id}")
	public void insertImageProduct(@PathVariable("id") String id) {
		User u = session.get("user");
		imageProductService.insertImageProduct("1", "default.webp", id);
		fileManagerService.changeImage(u.getUsername(), id);
	}
	
	@DeleteMapping("productvariant/delete/{id}")
	public void deleteProductVariant(@PathVariable("id") Integer id) {
		productVariantService.deleteById(id);
	}

}
