package com.Devex.Sevice.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Product;
import com.Devex.Repository.CustomerRepository;
import com.Devex.Repository.ProductRepository;
import com.Devex.Repository.UserRepository;
import com.Devex.Sevice.RecommendationSystem;

@SessionScope
@Service("recomendService")
public class RecommendationSystemImpl implements RecommendationSystem{
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	
	@Override
	public List<Product> recomendProduct(String username) {
		List<Product> recomendProductList = new ArrayList<>();
		List<Integer> categogyRecomend = customerRepository.recomendationSystem(username);
		System.out.println(categogyRecomend.toString());
		for (int categogy : categogyRecomend) {
			List<Product> rcmForCate = productRepository.findProductsByCategoryId(categogy);
			recomendProductList.addAll(rcmForCate);
		}
		
		return recomendProductList;
	}


	
}
