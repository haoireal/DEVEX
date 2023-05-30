package com.hnbcoffee.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hnbcoffee")
public class UserController {

	@GetMapping("/signin")
	public String showSignin() {
		return "account/signin";
	}
	@GetMapping("/signup")
    public String showSignup() {

		return "account/signup";
    }
	
}
