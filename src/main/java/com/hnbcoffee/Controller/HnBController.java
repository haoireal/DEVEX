package com.hnbcoffee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Sevice.ShoppingCartService;
import com.hnbcoffee.Utils.SessionService;


@Controller
@RequestMapping("/hnbcoffee")
public class HnBController {
	@Autowired
	ShoppingCartService cart;
	
	@Autowired
	SessionService session;
	
	@GetMapping("/home")
	public String homePageController() {
		session.set("cartCount", cart.getCount());
		return "user/index";
	}

	@GetMapping("/signin")
	public String signin() {
		session.set("cartCount", cart.getCount());
		return "account/signin";
	}
	@GetMapping("/signup")
	public String signupController() {
		session.set("cartCount", cart.getCount());
		return "account/signup";
	}
}