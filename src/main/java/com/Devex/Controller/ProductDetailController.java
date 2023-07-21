package com.Devex.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductDetailRepository;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;

@Controller
@RequestMapping("/devex")
public class ProductDetailController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductVariantService productVariantService;
	
	@RequestMapping("/details")
	public String details(ModelMap model ) {
		
		Product product = productService.findById("108-imtmbh293-10097").orElse(new Product()); // or any other default value
		
		System.out.println(product.getId());
		model.addAttribute("prodt", product);
		return "user/productDetail";
	}
	
	
}
