package com.hnbcoffee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.CookieService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.UserService;

@Controller
@RequestMapping("/hnbcoffee")
public class SigninController {
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	
	@GetMapping("/signout")
    public String doSignout() {
		session.remove("user");
		session.set("keywordsSearch", "");
		return "redirect:/hnbcoffee/home";
    }

	@GetMapping("/signin")
	public String showSignin(Model model) {
		model.addAttribute("email", cookie.getValue("email", ""));
		model.addAttribute("password", cookie.getValue("password", ""));
		return "account/signin";
	}
	
	@GetMapping("/404")
    public String get404() {
		return "admin/erorr404";
    }
	
	@PostMapping("/signin")
	public String doSignin(Model model) {
		String email = param.getString("email", "");
		String pass = param.getString("password", "");
		boolean remember = param.getBoolean("remember", false);
		User user = userService.checkLogin(email, pass);
		
		if(user != null) {
			if(user.isAcive()) {
				session.set("user", user);
				if(remember == true) {
					cookie.add("email", email, 10);
					cookie.add("password", pass, 10);
				}else {
					cookie.remove("email");
					cookie.remove("password");
				}
				
				if(user.getRole().equalsIgnoreCase("ADMIN")) {
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
