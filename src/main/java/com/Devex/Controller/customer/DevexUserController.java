package com.Devex.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;

@Controller
@RequestMapping("/user")
public class DevexUserController {

	@Autowired
	SessionService session;

	@Autowired
	CookieService cookie;

	@Autowired
	ParamService param;

	@GetMapping("/home")
	public String getHomePage() {

		return "user/index";
	}


	@GetMapping("/userProfile")
	public String getUserProfile() {

		return "admin/userManage/userProfile";
	}
}
