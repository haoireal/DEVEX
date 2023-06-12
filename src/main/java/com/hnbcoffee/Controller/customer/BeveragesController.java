package com.hnbcoffee.Controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hnbcoffee.DTO.TopBeverage;
import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Sevice.BeverageService;
import com.hnbcoffee.Sevice.OrderDetailService;
import com.hnbcoffee.Sevice.SessionService;



@Controller
@RequestMapping("/hnbcoffee")
public class BeveragesController {
	@Autowired
	BeverageService beverageService;
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
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
		model.addAttribute("count", beverageService.countByType(type));
		return "user/coffee";
	}
	
	@GetMapping("/beverage/bestseller")
	public String bestSeller(Model model) {
		List<TopBeverage> list = orderDetailService.findTopBeverage();
		List<Beverage> top = new ArrayList<>();
		int count = 0;
		for(TopBeverage item : list) {
			Beverage sp = item.getBeverage();
			top.add(sp);
			count ++;
		}
		model.addAttribute("beverages", top);
		model.addAttribute("count", count);
		return "user/coffee";
	}
	
	@GetMapping("/beverage/sort")
	public String priceSortAsc(Model model, @RequestParam("typeSort") String target) {
		String flag = null;
		Sort sort = null;
		if(target.equalsIgnoreCase("nor")) {
			return "redirect:/hnbcoffee/beverage";
		}
		if(target.equalsIgnoreCase("asc")) {
			sort = Sort.by(Direction.ASC, "price");
			flag = "asc";
		}else if(target.equalsIgnoreCase("desc")) {
			sort = Sort.by(Direction.DESC, "price");
			flag = "desc";
		}
		List<Beverage> list = beverageService.findAll(sort);
		model.addAttribute("beverages", list);
		model.addAttribute("count", beverageService.count());
		model.addAttribute("flag", flag);
		return "user/coffee";
	}
	
	@GetMapping("/beverage/search")
	public String searchBeverage(Model model, @RequestParam("search") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywordsSearch"));
		sessionService.set("keywordsSearch", kwords);
		List<Beverage> list = beverageService.findByKeywordName("%" + kwords + "%");
		model.addAttribute("beverages", list);
		model.addAttribute("count", beverageService.countByKeywordName("%" + kwords + "%"));
		return "user/coffee";
	}
	
	
	
	
	
	@ModelAttribute("sizes")
	public List<String> getSize() {
		List<String> list = new ArrayList<>();
		list.add("S");
		list.add("M");
		list.add("L");

		return list;
	}
	
	@ModelAttribute("quantities")
	public List<Integer> getQuantity() {
		List<Integer> quantities = new ArrayList<>();
		quantities.add(1);
		quantities.add(2);
		quantities.add(3);
		quantities.add(4);
		quantities.add(5);
		quantities.add(6);
		quantities.add(7);
		quantities.add(8);
		quantities.add(9);
		quantities.add(10);

		return quantities;
	}
}
