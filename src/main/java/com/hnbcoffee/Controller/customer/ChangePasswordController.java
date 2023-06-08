package com.hnbcoffee.Controller.customer;

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
public class ChangePasswordController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	
	@GetMapping("/change-password")
    public String showChangePass() {
		return "account/change-password";
    }
	
	@PostMapping("/change-password")
    public String doChangePass(Model model) {
		User user = session.get("user");
		String currentPass = param.getString("currentpassword", "");
		String newPass = param.getString("password", "");
		String cfPass = param.getString("cfpassword", "");
		if(user.getPassword().equals(currentPass)) {
			if(newPass.equals(cfPass)) {
				user.setPassword(newPass);
				userService.save(user);
			}else {
				model.addAttribute("message", "Confirm password does not match!");
				return "account/change-password";
			}
		}else {
			model.addAttribute("message", "Incorrect password!");
			return "account/change-password";
		}
		return "redirect:/hnbcoffee/home";
    }
}
