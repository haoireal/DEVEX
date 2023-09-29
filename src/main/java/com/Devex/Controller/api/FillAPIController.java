//package com.Devex.Controller.api;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Devex.DTO.ProductDTO;
//import com.Devex.Entity.Product;
//import com.Devex.Repository.ProductRepository;
//import com.Devex.Sevice.CookieService;
//import com.Devex.Sevice.ParamService;
//import com.Devex.Sevice.ProductService;
//import com.Devex.Sevice.RecommendationSystem;
//import com.Devex.Sevice.SessionService;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/api")
//public class FillAPIController {
//	@Autowired
//	SessionService sessionService;
//
//	@Autowired
//	CookieService cookieService;
//
//	@Autowired
//	ParamService paramService;
//
//	@Autowired
//	ProductService productService;
//
//	@Autowired
//	RecommendationSystem recomendationService;
//
//	@Autowired
//	ProductRepository productRepository;
//	
//	@GetMapping("/filter")
//	public List<ProductDTO> getProductDTO(){
//		List<Product> products = productService.findAll();
//		List<ProductDTO> productsList = products.stream()
//		    .map(pr -> {
//		        // Thực hiện chuyển đổi từ Product thành ProductDTO ở đây
//		        // Ví dụ:
//		        ProductDTO dto = new ProductDTO();
//		        dto.setId(pr.getId());
//		        dto.setName(pr.getName());
//		        dto.setDescription(pr.getDescription());
//		        dto.setCategoryDetails(pr.getCategoryDetails());
//		        dto.setActive(pr.getActive());
//		        dto.setIsdelete(pr.getIsdelete());
//		        dto.setSoldCount(pr.getSoldCount());
//		        dto.setSellerProduct(pr.getSellerProduct());
//		        dto.setCategoryDetails(pr.getCategoryDetails());
//		        dto.setProductbrand(pr.getProductbrand());
//		        dto.setImageProducts(pr.getImageProducts());
//		        dto.setProductVariants(pr.getProductVariants());
//		        dto.setComments(pr.getComments());
//		        // Điền các thuộc tính khác của ProductDTO ở đây
//		        return dto;
//		    })
//		    .collect(Collectors.toList());
//
//		return productsList;
//	}
//}
