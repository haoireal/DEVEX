package com.hnbcoffee.Controller.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Repository.BeverageCategoryRepository;
import com.hnbcoffee.Sevice.BeverageService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;

@Controller
@RequestMapping("/hnbcoffee/admin")
public class AdminController {
	@Autowired
	BeverageService beverageService;
	
	@Autowired
	BeverageCategoryRepository beverageCategoryRepository;
		
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ParamService paramService;
	
	@GetMapping("/home")
    public String getAdminHome() {
		return "admin/index";
    }
	
	@GetMapping("/sales")
    public String getAdminSales() {
		return "admin/sales";
    }
	
	

}
