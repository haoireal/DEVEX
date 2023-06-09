package com.hnbcoffee.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.CookieService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.UserService;

@Controller
@RequestMapping("/hnbcoffee")
public class UpdateProfileController {
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	
	@GetMapping("/profile")
	public String showProfile() {
		
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String doEditProfile() {
		User user = session.get("user");
		String fullname = param.getString("fullname", "");
		boolean gender = param.getBoolean("gender", false);
		String address = param.getString("address", "");
		user.setGender(gender);
		user.setFullname(fullname);
		user.setAddress(address);
		userService.save(user);
		return "redirect:/hnbcoffee/profile";
	}
}
