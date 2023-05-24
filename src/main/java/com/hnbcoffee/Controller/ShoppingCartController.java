package com.hnbcoffee.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Sevice.ShoppingCartService;
import com.hnbcoffee.Utils.ParamService;
import com.hnbcoffee.Utils.SessionService;

import jakarta.websocket.Session;


@Controller
@RequestMapping("/hnbcoffee")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService cart;
	
	@Autowired
	ParamService param;
	
	@Autowired
	SessionService session;
	
	@RequestMapping("/cart")
	public String view(Model model) {
		session.set("cart", cart);
		return "user/cart";
	}

	@RequestMapping("/cart/add/{id}")
	public String add(@PathVariable("id") Integer id) {
		cart.add(id);
		return "redirect:/hnbcoffee/cart";
	}
	
	@RequestMapping("/cart/remove/{id}")
	public String remove(@PathVariable("id") Integer id) {
		cart.remove(id);
		return "redirect:/hnbcoffee/cart";
	}
	
	@RequestMapping("/cart/update")
	public String update() {
		Integer id = param.getInteger("id", 0);
		int qty = param.getInteger("qty", 0);
		cart.update(id, qty);
		return "redirect:/hnbcoffee/cart";
	}
	
	@RequestMapping("/cart/clear")
	public String clear() {
		cart.clear();
		return "redirect:/hnbcoffee/cart";
	}
	
	@ModelAttribute("sizes")
	public Map<String, String> geSize() {
		Map<String, String> map = new HashMap<>();
		map.put("S", "0");
		map.put("M", "5000");
		map.put("L", "10000");

		return map;
	}
	
	
}
