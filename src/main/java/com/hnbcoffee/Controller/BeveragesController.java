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
public class BeveragesController {
	@Autowired
	BeverageService beverageService;
	
//	@Autowired
//	TypeOfBeverageRepository typeOfBeverageRepository;
	
	@GetMapping("/beverage")
	public String coffeeController(Model model) {
		List<Beverage> list = beverageService.findAll();
		model.addAttribute("beverages", list);
		model.addAttribute("count", beverageService.count());
		return "user/coffee";
	}
	
	@GetMapping("/beverage/detail/{beverageId}")
    public String showProductDetail(@PathVariable("beverageId") Integer beverageId, Model model) {
		Beverage item = beverageService.findById(beverageId).get();
        model.addAttribute("beverage", item);
        return "user/beveragedetail";
    }
	
	@GetMapping("/beverage/{type}")
	public String typeCoffeeController(Model model, @PathVariable("type") String type) {
		List<Beverage> list = beverageService.findByType(type);
		model.addAttribute("beverages", list);
		model.addAttribute("count", beverageService.count());
		return "user/coffee";
	}
	
	
	@ModelAttribute("sizes")
	public Map<String, String> getSize() {
		Map<String, String> map = new HashMap<>();
		map.put("S", "0");
		map.put("M", "5000");
		map.put("L", "10000");

		return map;
	}
}
