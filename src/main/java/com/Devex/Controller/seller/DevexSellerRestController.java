package com.Devex.Controller.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.Devex.DTO.StatisticalRevenueMonthDTO;
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
import com.Devex.Sevice.FollowService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
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
	private FileManagerService fileManagerService;

	@Autowired
	private SessionService session;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryDetailService categoryDetailService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ImageProductService imageProductService;
	
	@Autowired
	private ProductBrandService brandService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FollowService followService;
	
	@Autowired
	private OrderDetailService detailService;

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
	
	@SuppressWarnings("deprecation")
	@GetMapping("/revenue/gettotalprice")
	public Map<String, Object> getrevenue() {
		User u = session.get("user");
		Map<String, Object> mapStatistical = new HashMap<>();
		int amountOrder = orderService.getCountOrderForSeller(u.getUsername());
		int amountFollow = followService.getCountFollowBySellerUsername(u.getUsername());
		int amountProduct = productService.getCountProductBySellerUsername(u.getUsername());
		Double totalRevenue = orderService.getTotalOrderValueForSeller(u.getUsername());
		mapStatistical.put("amountOrder", amountOrder);
		mapStatistical.put("totalRevenue", totalRevenue);
		mapStatistical.put("amountFollow", amountFollow);
		mapStatistical.put("amountProduct", amountProduct);
		return mapStatistical;
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/revenue/month")
	public List<StatisticalRevenueMonthDTO> getRevenueByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
		User u = session.get("user");
		int yearCompare;
		int monthCompare;
		if(month == 1) {
			yearCompare = year - 1;
			monthCompare = 12;
		}else {
			yearCompare = year;
			monthCompare = month - 1;
		}
        List<Object[]> listt = detailService.getTotalPriceByMonthAndSellerUsername(year, month, u.getUsername());
        List<Object[]> listtc = detailService.getTotalPriceByMonthAndSellerUsername(yearCompare, monthCompare, u.getUsername());
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		LocalDate date = LocalDate.of(year, month, 1);
		LocalDate dateCompare = LocalDate.of(yearCompare, monthCompare, 1);
        int lengthOfMonth = date.lengthOfMonth();
        int lengthOfMonthCompare = dateCompare.lengthOfMonth();
        if(lengthOfMonth > lengthOfMonthCompare) {
        	for (int i = 1; i <= lengthOfMonth; i++) {
                double price = 0;
                double priceCompare = 0;
                for (Object[] ob : listt) {
                    int day = (ob[0] == null) ? 0 : (int) ob[0];
                    if (day == i) {
                    	price = (ob[1] == null) ? 0 : (double) ob[1];
                        break;
                    }
                }
                for (Object[] obc : listtc) {
                    int day = (obc[0] == null) ? 0 : (int) obc[0];
                    if (day == i) {
                    	priceCompare = (obc[1] == null) ? 0 : (double) obc[1];
                        break;
                    }
                }
                StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
                statistical.setDay(i);
                statistical.setPrice(price);
                statistical.setPriceCompare(priceCompare);
                liststatis.add(statistical);
            }
        }else {
        	for (int i = 1; i <= lengthOfMonthCompare; i++) {
                double price = 0;
                double priceCompare = 0;
                for (Object[] ob : listt) {
                    int day = (ob[0] == null) ? 0 : (int) ob[0];
                    if (day == i) {
                    	price = (ob[1] == null) ? 0 : (double) ob[1];
                        break;
                    }
                }
                for (Object[] obc : listtc) {
                    int day = (obc[0] == null) ? 0 : (int) obc[0];
                    if (day == i) {
                    	priceCompare = (obc[1] == null) ? 0 : (double) obc[1];
                        break;
                    }
                }
                StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
                statistical.setDay(i);
                statistical.setPrice(price);
                statistical.setPriceCompare(priceCompare);
                liststatis.add(statistical);
            }
        }
        return liststatis;
	}

}
