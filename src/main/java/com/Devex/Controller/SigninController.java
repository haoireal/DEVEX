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
public class SigninController {
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
	
	@GetMapping("/signout")
    public String doSignout() {
		session.remove("user");
//		session.set("keywordsSearch", "");
		return "redirect:/home";
    }
	
	@GetMapping("/404")
    public String get404() {
		return "admin/erorr404";
    }
	
	@PostMapping("/signin")
	public String doSignin(Model model) {
		String username = param.getString("username", "");
		String pass = param.getString("password", "");
		boolean remember = param.getBoolean("remember", false);
		User user = userService.checkLogin(username, pass);
		System.out.println(user.getFullname());
		if(user != null) {
			//fix tạm thời
			if(user.getRole().getName().equalsIgnoreCase("Seller")) {
				session.set("user", user);
				return "redirect:/seller/home";
			}
			//end fix tạm thời
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
					session.set("user", user);
					return "redirect:/home";
				}
//				if(user.getRole().getName().equalsIgnoreCase("Seller")) {
//					session.set("user", user);
//					return "redirect:/seller/home";
//				}
				else {
					session.set("user", user);
					return "redirect:/admin/home";
				}
			}else {
				model.addAttribute("message", "Tài khoản này đã bị khoá!");
				return "account/signin";
			}
			
		}else {
			model.addAttribute("message", "Đăng nhập thất bại!");
			return "account/signin";
		}
	}
}
