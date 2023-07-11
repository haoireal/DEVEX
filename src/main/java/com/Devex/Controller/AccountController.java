package com.Devex.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;
import com.Devex.Entity.User;


@Controller
@RequestMapping("/devex")
public class AccountController {
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	

	@GetMapping("/signin")
	public String showSignin(Model model) {
		model.addAttribute("username", cookie.getValue("username", ""));
		model.addAttribute("password", cookie.getValue("password", ""));
		return "account/signin";
	}
	
	@PostMapping("/signin")
	public String doSignin(Model model) {
		String username = param.getString("username", "");
		String pass = param.getString("password", "");
		boolean remember = param.getBoolean("remember", false);
		User user = userService.checkLogin(username, pass);
		
		if(user != null) {
			if(user.getActive()) {
				session.set("user", user);
				if(remember == true) {
					cookie.add("username", username, 10);
					cookie.add("password", pass, 10);
				}else {
					cookie.remove("username");
					cookie.remove("password");
				}
				
				if(user.getRole().getName().equalsIgnoreCase("Customer")) {
					return "redirect:/hnbcoffee/admin/home";
				}else {
					return "redirect:/hnbcoffee/home";
				}
			}else {
				model.addAttribute("message", "This account is blocked!");
				return "account/signin";
			}
			
		}else {
			model.addAttribute("message", "Sign in fail!");
			return "account/signin";
		}
	}
}
