package com.Devex.Controller.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;

@Controller

public class ProductDetailController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductVariantService productVariantService;
	
	@Autowired
	ProductRepository productRepository;

	

	@RequestMapping("/details/{id}")
	public String details(ModelMap model, @PathVariable("id") String id) {
		Product seller = productService.findProductById(id);
		Product product = productService.findById(id).orElse(new Product());
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

		String[] name = product.getName().split("\\s+");
		List<Product> list = new ArrayList<>();
		Set<Product> uniqueProducts = new HashSet<>(); // check trùng
		// sản phẩm của shop
		List<Product> listPrS = productService.findProductBySellerUsername("%" + seller.getSellerProduct().getUsername() + "%");
		listPrS.remove(product);
		// Tìm tên từ theo từ khóa
		for(int i = 0; i < 3 ; i++) {
			list.addAll(productService.findByKeywordName(name[i]));
			list.forEach(pr ->{
				uniqueProducts.add(pr);
			});
			
		}
		 // Chuyển đổi lại thành danh sách (List)
        List<Product> uniqueProductList = new ArrayList<>(uniqueProducts);
		// Tìm theo shop bán
        uniqueProductList.removeAll(productService.findProductBySellerUsername("%" + seller.getSellerProduct().getUsername() + "%"));
//		Collections.shuffle(uniqueProductList);
		
		model.addAttribute("shopProduct", listPrS); // sản phẩm khác của shop
		model.addAttribute("products", uniqueProductList);// gợi ý sản phẩm theo keyword
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
			Double price =  productVariantService.findPriceByColorAndSize(id, color, size);
			return price;
	}

}
