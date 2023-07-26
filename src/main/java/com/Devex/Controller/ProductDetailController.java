package com.Devex.Controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.Devex.Repository.ProductDetailRepository;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;

@Controller

public class ProductDetailController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductVariantService productVariantService;

	@RequestMapping("/details/{id}")
	public String details(ModelMap model, @PathVariable("id") String id) {
//		System.out.println("ssssssssss"+id);
		Product product = productService.findById(id).orElse(new Product()); // or any other default value
//		Map<String, Integer> mapSize = new HashMap<>();
//		Map<String, Integer> mapColor = new HashMap<>();
		//list Size
		List<String> listSize = new ArrayList<>();

		product.getProductVariants().forEach(sv -> {
			if (!listSize.contains(sv.getSize())) {
				listSize.add(sv.getSize());
				System.out.println(sv.getSize());
			}
		});
		
		
		//ListColor
		List<String> listColor = new ArrayList<>();

		product.getProductVariants().forEach(sv -> {
			if (!listColor.contains(sv.getColor())) {
				listColor.add(sv.getColor());
				System.out.println(sv.getColor());
			}
		});
		
		model.addAttribute("listColor", listColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("prodt", product);

		return "user/productDetail";
	}

}
