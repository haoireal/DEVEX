package com.hnbcoffee.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.*;


@Controller
@RequestMapping("/hnbcoffee")
public class HistoryController {
	
	@Autowired
	SessionService session;
	
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/history")
	public String showHistory(Model model) {
		User user = session.get("user", null);
		if(user != null) {
			model.addAttribute("list", orderService.findByAccount(user));
			return "user/history";
		}else {
			return "redirect:/hnbcoffee/signin";
		}
		
	}

}