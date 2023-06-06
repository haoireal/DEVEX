package com.hnbcoffee.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.DTO.CartItem;
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
	
	@RequestMapping("/cart")
	public String view(Model model) {
		model.addAttribute("cart", cart);
		session.set("cartCount", cart.getCount());
		return "user/cart";
	}

	@RequestMapping("/cart/add/{id}")
	public String add(@PathVariable("id") Integer id) {
		CartItem item = new CartItem();
		item.setId(id);
		item.setName(param.getString("name", null));
		item.setImage(param.getString("image", null));
		item.setPrice(param.getDouble("price", 0));
		item.setSize(param.getString("size", "S"));
		item.setQty(param.getInteger("qty", 1));
		cart.add(item);
		System.out.print(item);
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
	public Map<String, String> getSize() {
		Map<String, String> map = new HashMap<>();
		map.put("S", "0");
		map.put("M", "5000");
		map.put("L", "10000");

		return map;
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
	
//	@PostMapping("/cart/update")
//    @ResponseBody
//    public Map<String, Integer> changeSelectValue(@RequestParam("selectedValue") Integer selectedValue) {
//		// Xử lý logic và thay đổi giá trị
//		Integer newOptionValue = selectedValue;
//		Integer id = param.getInteger("id", 0);
//		int qty = param.getInteger("qty", 0);
//		cart.update(id, qty);
//		// Tạo đối tượng phản hồi
//		Map<String, Integer> response = new HashMap<>();
//		response.put("newValue", newOptionValue);
//		    
//		return response;
//    }
	
	
}
