package com.Devex.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;

@Controller
public class DevexController {

	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	@Autowired
	ProductRepository dao;
	
	@GetMapping("/home")
    public String doDemo() {
	
		return "user/index";
    }
	
}
