package com.hnbcoffee.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

//	@GetMapping("/signin")
//	public String showSignin() {
//		return "account/signin";
//	}
//	@GetMapping("/signup")
//    public String showSignup() {
//
//		return "account/signup";
//    }
	
	@GetMapping("/home")
	public String showSignin() {
		return "This is home Page";
	}
	@GetMapping("/admin")
    public String showSignup() {

		return "This is admin page";
    }
}
