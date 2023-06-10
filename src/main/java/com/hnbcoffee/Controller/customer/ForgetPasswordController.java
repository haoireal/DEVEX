package com.hnbcoffee.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.*;


@Controller
@RequestMapping("/hnbcoffee")
public class ForgetPasswordController {

	@Autowired
	ParamService param;
	
	@Autowired
	MailerService emailService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/forgetpassword")
	public String showForgetPassword() {
		return "account/forgetpassword";
	}
	
	@PostMapping("/forgetpassword")
	public String doForgetPassword(Model model) {
		String email = param.getString("email", "");
		User user = userService.findByEmailLike(email);
		if(user != null) {
			try {
				emailService.sendMailToFormat("forget", user);
			} catch (Exception e) {
				return e.getMessage();
			}
			model.addAttribute("message", "Check email, please!");
		}else {
			model.addAttribute("message", "Incorrect email");
		}
		
		return "account/forgetpassword";
	}

}