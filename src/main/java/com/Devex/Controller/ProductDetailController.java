package com.Devex.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.Product;
import com.Devex.Entity.Seller;
import com.Devex.Repository.ProductDetailRepository;
import com.Devex.Repository.SellerRepository;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;




@Controller

public class ProductDetailController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductVariantService productVariantService;
	
	@Autowired
	ProductDetailRepository ProductDetailRepository;
	
	@RequestMapping("/details/{id}")
	public String details(ModelMap model, @PathVariable("id") String id) {
		Product product = productService.findById(id).orElse(new Product()); 
		List<String> listSize = new ArrayList<>();
		product.getProductVariants().forEach(sv -> {
			if (!listSize.contains(sv.getSize())) {
				listSize.add(sv.getSize());
			}
		});
			
		//ListColor
		List<String> listColor = new ArrayList<>();

		product.getProductVariants().forEach(sv -> {
			if (!listColor.contains(sv.getColor())) {
				listColor.add(sv.getColor());
			}
		});
		
		
		Product seller = ProductDetailRepository.findProductById(id);
		
		
		model.addAttribute("listColor", listColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("product", product);
		model.addAttribute("seller", seller);

		return "user/productDetail";
	}

}
