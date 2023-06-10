package com.hnbcoffee.Controller.customer;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.CookieService;
import com.hnbcoffee.Sevice.MailerService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.UserService;

import jakarta.mail.MessagingException;

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
	
	@Autowired
	MailerService emailService;
	
	@GetMapping("/signup")
    public String showSignup() {
		return "account/signup";
    }
	
	@GetMapping("/verifi")
    public String showVerifi() {
		return "account/verifi";
    }
	
	@PostMapping("/signup")
	public String doSignup(Model model) {
		String fullname = param.getString("fullname", "");
		String email = param.getString("email", "");
		String address = param.getString("address", "");
		Date birthday = param.getDate("birthday", "yyyy-MM-dd");
		System.out.println(birthday.toString());
		String gender = param.getString("gender", "");
		String pass = param.getString("password", "");
		//Tạo user mới
		User newUser  = new User();
		//THêm dữ liệu vào user mới
		newUser.setFullname(fullname);
		newUser.setEmail(email);
		newUser.setAddress(address);
		newUser.setBirthday(birthday);
		newUser.setGender(((gender.equalsIgnoreCase("MALE")) ? true : false));
		newUser.setPassword(pass);
		newUser.setRole("CUSTOMER");
		//tạo mã xác nhận ngẫu nhiên
		Random random = new Random();
		int vericode = random.nextInt(90000) + 10000;
		newUser.setVericode(vericode);
		newUser.setAcive(Boolean.FALSE);
		//Thêm user mới
		User user = userService.save(newUser);
		
		if (user != null) {
			session.set("user", user);
			System.out.println("email ne: "+user.getEmail());
			
			try {
				emailService.sendMailToFormat("verifi", user);
			} catch (MessagingException e) {
				// TODO: handle exception
				return e.getMessage();
			}
			return "redirect:/hnbcoffee/verifi";
		} else {
			return "redirect:/hnbcoffee/login";
		}
		
	}
	
	@PostMapping("/verifi")
	public String doVerifi(Model model) {
		//lấy code nhập từ html
		String code = "";
		for (int i = 1; i <= 5; i++) {
			code += param.getString("o" + i,"");
		}
		System.out.println("input is " + code);
		
		User user = (User) session.get("user");
		
		if (user.getVericode() == Integer.parseInt(code)) {
			user.setAcive(true);
			userService.save(user);
			session.set("user", user);
			return "redirect:/hnbcoffee/signin";
		} else {
			return "redirect:/hnbcoffee/verifi";
		}
	}
}
