package com.Devex.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.User;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;
import java.util.List;
import ch.qos.logback.core.model.Model;

@Controller
public class AccountController {
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
		return "account/email-phone";
	}
	
	@PostMapping("/signup")
	public String sendSmsSignup(Model model, @RequestParam("email-phone") String info) {
		List<User> list = userService.findAll();
		
		return "account/verifi";
	}
	
	@GetMapping("/signup-information")
	public String showInfSignup() {
		return "account/signup";
	}
	
	@GetMapping("/verify")
	public String showVerify() {
		return "account/verifi";
	}
	
	@GetMapping("/change-password")
	public String showChangePassword() {
		return "account/current-password";
	}
	
	@GetMapping("/forget-password")
	public String showForgetPassword() {
		return "account/new-password";
	}
	
}
