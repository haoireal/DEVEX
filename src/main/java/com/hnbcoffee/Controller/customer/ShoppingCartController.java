package com.hnbcoffee.Controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.DTO.CartItem;
import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.MailerService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.ShoppingCartService;


@Controller
@RequestMapping("/hnbcoffee")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService cart;
	
	@Autowired
	ParamService param;
	
	@Autowired
	SessionService session;
	
	@Autowired
	MailerService mailService;
	
	@RequestMapping("/cart")
	public String view(Model model) {
		User user = session.get("user", null);
		if(user != null) {
			model.addAttribute("cart", cart);
			session.set("cartCount", cart.getCount());
			return "user/cart";
		}else {
			return "redirect:/hnbcoffee/signin";
		}
		
	}

	@RequestMapping("/cart/add/{id}")
	public String add(@PathVariable("id") Integer id) {
		User user = session.get("user", null);
		if(user != null) {
			CartItem item = new CartItem();
			// Tạo một đối tượng Random
	        Random random = new Random();
	        // Sinh số ngẫu nhiên có 4 chữ số
	        int randomNumber = random.nextInt(9000) + 1000;
	        CartItem item2 = cart.getItemById(randomNumber);
	        while (item2 != null) {
	            randomNumber = random.nextInt(9000) + 1000;
	        }
			item.setId(randomNumber);
			item.setIdBeverage(id);
			item.setName(param.getString("name", null));
			item.setImage(param.getString("image", null));
			item.setPrice(param.getDouble("price", 0));
			item.setSize(param.getString("size", "S"));
			item.setQty(param.getInteger("qty", 1));
			cart.add(item);
			return "redirect:/hnbcoffee/cart";
		}else {
			return "redirect:/hnbcoffee/signin";
		}
		
		
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
		String size = param.getString("sz", "S");
		cart.update(id, qty, size);
		return "redirect:/hnbcoffee/cart";
	}
	
	@RequestMapping("/cart/clear")
	public String clear() {
		cart.clear();
		return "redirect:/hnbcoffee/cart";
	}
	
	@RequestMapping("/cart/order")
	public String order() {
		User user = session.get("user");
		if(user != null) {
			try {
				mailService.sendMailToFormat("order", user);
				cart.clear();
			} catch (Exception e) {
				return e.getMessage();
			}
		}else {
			return "redirect:/hnbcoffee/cart";
		}
		return "redirect:/hnbcoffee/cart";
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
