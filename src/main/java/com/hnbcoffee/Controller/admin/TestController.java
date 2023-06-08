package com.hnbcoffee.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hnbcoffee/admin")
public class TestController {
	@GetMapping("/home")
    public String test1() {
		return "admin/index";
    }
	
	@GetMapping("/beverage")
    public String test2() {
		return "admin/beverage";
    }
	@GetMapping("/sales")
    public String test3() {
		return "admin/sales";
    }
	@GetMapping("/account")
    public String test4() {
		return "admin/account";
    }
	
}
