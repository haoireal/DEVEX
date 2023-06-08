package com.hnbcoffee.Controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Sevice.BeverageService;


@Controller
@RequestMapping("/hnbcoffee")
public class TestController {
	@Autowired
	BeverageService beverageService;
	
//	@Autowired
//	TypeOfBeverageRepository typeOfBeverageRepository;
	
	@GetMapping("/admin")
	public String coffeeController(Model model) {
		
		return "admin/index";
	}
	@GetMapping("/admin/account")
	public String coffeeeController(Model model) {
		
		return "admin/account";
	}
	
	@GetMapping("/admin/beverage")
	public String coffeeeeController(Model model) {
		
		return "admin/beverage";
	}
	
	@GetMapping("/admin/sales")
	public String coffeeeeeController(Model model) {
		
		return "admin/sales";
	}

}
