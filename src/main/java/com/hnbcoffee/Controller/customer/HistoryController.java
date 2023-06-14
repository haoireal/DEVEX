package com.hnbcoffee.Controller.customer;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.Order;
import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.OrderDetailService;
import com.hnbcoffee.Sevice.OrderService;
import com.hnbcoffee.Sevice.SessionService;



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
			List<Order> orders = orderService.findByAccount(user);
			orders.sort(Comparator.comparing(Order::getDate).reversed());
			model.addAttribute("list", orders);
			return "user/history";
		}else {
			return "redirect:/hnbcoffee/signin";
		}
		
	}

}