package com.Devex.Controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Repository.ProductVariantRepository;



@Controller
@RequestMapping("/devex")
public class FindController {

	@Autowired
	ProductVariantRepository proDao;
	
	
//	
//		@RequestMapping("find")
//		public String find(ModelMap model) {
//			Pageable pageable = PageRequest.of(0, 30);
//			Page<Product> product = proDao.findAllProducts(pageable);//hiện thị all sản phẩm lên 
//			model.addAttribute("product", product);
//			
//			List<Product> proramdom = proDao.findRandomProducts();
//			model.addAttribute("proramdom", proramdom);
//			return "user/findproduct";
//		}
}
