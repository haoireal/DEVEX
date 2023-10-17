package com.Devex.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Devex.Sevice.CartDetailService;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class  OrderController {
	@Autowired
	CartDetailService cartDetailService;
	@Autowired
	CartService cartService;
	@Autowired
	CustomerService customerService;
	@Autowired
	SessionService session;

	@Autowired
	HttpServletResponse resp;
	@Autowired
	HttpServletRequest req;
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@GetMapping("/cart/detail-order")
	public String showDetailOrder(Model model) {
		return "user/cartproductFake";
	}
	
	
	
}
