package com.hnbcoffee.Controller.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Entity.Order;
import com.hnbcoffee.Entity.OrderDetail;
import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Sevice.BeverageService;
import com.hnbcoffee.Sevice.MailerService;
import com.hnbcoffee.Sevice.OrderDetailService;
import com.hnbcoffee.Sevice.OrderService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.ShoppingCartService;
import com.hnbcoffee.Sevice.UserService;

import jakarta.servlet.http.HttpServletResponse;


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
	
	@Autowired
	UserService userService;
	
	@Autowired
	BeverageService beverageService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	HttpServletResponse resp;
	@RequestMapping("/cart")
	public String view(Model model) {
		User user = session.get("user", null);
		if(user != null) {
			model.addAttribute("cart", cart);
			session.set("cartCount", cart.getCount());
			if(user.getRole().equalsIgnoreCase("ADMIN")) {
				List<User> list = userService.findAll();
				model.addAttribute("users", list);
			}
			
			return "user/cart";
		}else{
			return "redirect:/hnbcoffee/signin?message=Plesse signin before order!";
		}
		
	}

	@RequestMapping("/cart/add/{id}")
	public String add(@PathVariable("id") Integer id, Model model) {
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
			model.addAttribute("cart", cart);
			session.set("cartCount", cart.getCount());
			return "redirect:/hnbcoffee/beverage/detail/" + id;
		}else {
			return "redirect:/hnbcoffee/signin?message=Plesse signin before order!";
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
				//tạo bill
				Order order = new Order();
				Date date = new Date();
				order.setDate(date);
				order.setTotal(cart.getAmount());
				order.setCustomer(user);
				orderService.save(order);
				//thêm sp vào bill
				for (CartItem item : cart.getItems()) {
					OrderDetail od = new OrderDetail();
					Beverage sp = beverageService.findById(item.getIdBeverage()).get();
					od.setBeverage(sp);
					od.setPrice(item.getPrice() + priceSize(item.getSize()));
					od.setQuantity(item.getQty());
					od.setSize(item.getSize());
					od.setOrder(order);
					orderDetailService.save(od);
				}
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
	
	public long priceSize(String size) {
		if(size.equalsIgnoreCase("L")) {
			return 10000;
		}else if(size.equalsIgnoreCase("M")) {
			return 5000;
		}else {
			return 0;
		}
	}
	
	@RequestMapping("/cart/admin/order")
	public String orderAdmin(@ModelAttribute("email") String customerEmail) {
		User user = userService.findByEmailLike(customerEmail);
		
		System.out.println(customerEmail);
		
		if(user != null) {
			try {
				//tạo bill
				Order order = new Order();
				Date date = new Date();
				order.setDate(date);
				order.setTotal(cart.getAmount());
				order.setCustomer(user);
				orderService.save(order);
				//thêm sp vào bill
				for (CartItem item : cart.getItems()) {
					OrderDetail od = new OrderDetail();
					Beverage sp = beverageService.findById(item.getIdBeverage()).get();
					od.setBeverage(sp);
					od.setPrice(item.getPrice() + priceSize(item.getSize()));
					od.setQuantity(item.getQty());
					od.setSize(item.getSize());
					od.setOrder(order);
					orderDetailService.save(od);
				}
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
