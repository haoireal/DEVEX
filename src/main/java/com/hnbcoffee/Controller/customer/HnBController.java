package com.hnbcoffee.Controller.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.DTO.Top6Beverage;
import com.hnbcoffee.Sevice.OrderDetailService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.ShoppingCartService;


@Controller
@RequestMapping("/hnbcoffee")
public class HnBController {
	@Autowired
	ShoppingCartService cart;
	
	@Autowired
	SessionService session;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/home")
	public String homePageController(Model model) {
		session.set("cartCount", cart.getCount());
		Pageable pageable = PageRequest.of(0, 6);
		Page<Top6Beverage> list = orderDetailService.findTop6Beverage(pageable);
		model.addAttribute("listTop6", list);
		return "user/index";
	}

}