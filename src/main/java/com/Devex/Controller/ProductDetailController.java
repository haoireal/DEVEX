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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Devex.Entity.Product;
import com.Devex.Entity.Seller;
import com.Devex.Repository.ProductDetailRepository;
import com.Devex.Repository.ProductVariantRepository;
import com.Devex.Repository.SellerRepository;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;

import io.jsonwebtoken.io.IOException;
import lombok.Data;

@Controller

public class ProductDetailController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductVariantService productVariantService;

	

	@RequestMapping("/details/{id}")
	public String details(ModelMap model, @PathVariable("id") String id) {
//		System.out.println("sssss"+id);
		Product product = productService.findById(id).orElse(new Product());

		System.out.println(">>>>>>" + product.getBrand());

		List<String> listSize = new ArrayList<>();
		product.getProductVariants().forEach(sv -> {
			if (!listSize.contains(sv.getSize())) {
				listSize.add(sv.getSize());
			}
		});

		// ListColor
		List<String> listColor = new ArrayList<>();

		product.getProductVariants().forEach(sv -> {
			if (!listColor.contains(sv.getColor())) {
				listColor.add(sv.getColor());
			}
		});

		Product seller = productService.findProductById(id);

		model.addAttribute("listColor", listColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("product", product);
		model.addAttribute("seller", seller);

		return "user/productDetail";
	}

	// Phương thức nhận dữ liệu từ request
	@RequestMapping(value = ("/data"), method = RequestMethod.POST)
	@ResponseBody
	public Double Data(@RequestParam("id") String id, @RequestParam("color") String color,
			@RequestParam("size") String size) {
		System.out.println("sssssss: " + size);
		Double price = 0.0;
//		if(size != null) {
			 price = productVariantService.findPriceByColorAndSize(id, color, size);
//		}else {
////			price = productVariantService.findPriceByColor(id, color);
//		}
		
		return price;
	}

}
