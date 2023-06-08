package com.hnbcoffee.Controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.CookieService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.UserService;

@Controller
@RequestMapping("/hnbcoffee")
public class SignupController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;

	
	
	
	@GetMapping("/signup")
    public String showSignup() {
		return "account/signup";
    }
	

}
