package com.hnbcoffee.Controller;

import com.hnbcoffee.Utils.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.Beverage;

@Controller
@RequestMapping("/hnbcoffee")
public class HnBController {
	@GetMapping("/home")
	public String homePageController() {
		return "user/index";
	}
	@GetMapping("/coffee")
	public String coffeeController(Model model) {
		model.addAttribute("beverages", DataSharing.beverages.values());
		return "user/coffee";
	}
	@GetMapping("/coffee/detail/{beverageId}")
    public String showProductDetail(@PathVariable("beverageId") int beverageId, Model model) {
		Beverage beverage = DataSharing.beverages.get(beverageId);
		System.out.println(beverage.toString());
        model.addAttribute("beverage", beverage);
        return "user/beveragedetail";
    }
	
	@GetMapping("/cart")
    public String cartDetail() {
		
        return "user/cart";
    }
}