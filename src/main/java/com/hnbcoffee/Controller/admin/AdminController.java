package com.hnbcoffee.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hnbcoffee/admin")
public class AdminController {
	
	@GetMapping("/home")
    public String getAdminHome() {
		return "admin/index";
    }
	
	@GetMapping("/beverage")
    public String getAdminBeverage() {
		return "admin/beverage";
    }
	
	@GetMapping("/sales")
    public String getAdminSales() {
		return "admin/sales";
    }
	
	@GetMapping("/account")
    public String getAdminAccount() {
		return "admin/account";
    }
}
