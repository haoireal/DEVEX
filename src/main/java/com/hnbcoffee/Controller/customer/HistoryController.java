package com.hnbcoffee.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Sevice.*;


@Controller
@RequestMapping("/hnbcoffee")
public class HistoryController {
	
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/history")
	public String showHistory(Model model) {
		model.addAttribute("list", orderService.findAll());
		return "user/history";
	}

}