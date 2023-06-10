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
public class AdminBeverageController {
	@Autowired
	BeverageService beverageService;
	
	@Autowired
	BeverageCategoryRepository beverageCategoryRepository;
		
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ParamService paramService;
	
	@GetMapping("/beverage")
	public String coffeeController(Model model) {
		List<Beverage> list = beverageService.findAll();
		model.addAttribute("beverage", new Beverage());
		model.addAttribute("beverages", list);
		return "admin/beverage";
	}
	
	@RequestMapping("/beverage/create")
	public String create(Beverage b) {
		beverageService.save(b); //Size S
		return "redirect:/hnbcoffee/admin/beverage";
	}
	
	@RequestMapping("/beverage/update/{id}")
	public String update(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		String beveragename = paramService.getString("beveragename", "");
		Double price = paramService.getDouble("price", 0.0);
		String description = paramService.getString("description", "");
		Integer category = paramService.getInteger("category", 0);
		System.out.println(category);
		String image = paramService.getString("image", "");
		Beverage b = new Beverage();
		b.setId(id); System.out.println(id);
		b.setName(beveragename);
		b.setPrice(price);
		b.setDescription(description);
		b.setCategory(beverageCategoryRepository.findById(category).orElse(null) );
		b.setImage(image);
		beverageService.save(b);
		return "redirect:/hnbcoffee/admin/beverage";
	}
	
	@RequestMapping("/beverage/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			beverageService.deleteById(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Dữ liệu bạn xoá có ảnh hưởng tới các dữ liệu khác");
		}
		return "redirect:/hnbcoffee/admin/beverage";
	}
	
}
