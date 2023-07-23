package com.Devex.Controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.CartProdcut;
import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ShoppingCartDTO;
import com.Devex.Sevice.ShoppingCartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aj.org.objectweb.asm.TypeReference;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {
	@Autowired
	ProductRepository daop;
	@Autowired
	HttpServletResponse resp;
	@Autowired
	HttpServletRequest req;
	@Autowired
	ShoppingCartService cart;
	
	ObjectMapper objectMapper = new ObjectMapper();
	Cookie cookie =null;
	@RequestMapping("/devex/productPRO")
	public String showProdcutDetail(Model model) {
		List<Product> product = daop.findAll();
		model.addAttribute("products", product);
		return "user/demoProductdetal";
	}
	@RequestMapping("/devex/cart")
	public String showcart(Model model , @CookieValue(value ="myCart",required = false) String cartValue) {
		if (cartValue != null && !cartValue.isEmpty()) {
			// Giải mã chuỗi Base64
			byte[] decodedBytes = Base64.decodeBase64(cartValue);
			String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);

			try {
				Map<String, CartProdcut> map = objectMapper.readValue(decodedValue, new com.fasterxml.jackson.core.type.TypeReference<Map<String, CartProdcut>>() {
				});
				cart.setItems(map);
			} catch (JsonProcessingException e) {
				// Xử lý lỗi khi chuyển đổi chuỗi JSON
			}
			model.addAttribute("cart", cart);
			model.addAttribute("total", cart.getAmount());
			List<Product> list = daop.findAll();
			model.addAttribute("test", list);
			
		}
		return "user/cartproduct";
	}
	@RequestMapping("/devex/cartproduct/add/{idProduct}")
	public String addCart(@PathVariable("idProduct") String id, Model model) throws JsonProcessingException {
		
		cart.add(id);
	
		Map<String, CartProdcut> itemsMap = cart.getItems().stream()
				.collect(Collectors.toMap(CartProdcut::getName, Function.identity()));
		String cartValue = objectMapper.writeValueAsString(itemsMap);
	
		// Mã hóa chuỗi JSON thành chuỗi Base64
		byte[] encodedBytes = Base64.encodeBase64(cartValue.getBytes(StandardCharsets.UTF_8));
		String encodedCartValue = new String(encodedBytes, StandardCharsets.UTF_8);
		cookie = new Cookie("myCart", encodedCartValue);
		cookie.setMaxAge(86400);
		cookie.setPath("/");
		resp.addCookie(cookie);
		System.out.println(cart.getCount());
		return "redirect:/devex/productPRO";
	}
	@RequestMapping("/devex/cartproduct/remove/{idProduct}")
	public String remove(@PathVariable("idProduct") String id) throws JsonProcessingException {
		cart.remove(id);
		Map<String, CartProdcut> itemsMap = cart.getItems().stream()
				.collect(Collectors.toMap(CartProdcut::getName, Function.identity()));
		String cartValue = objectMapper.writeValueAsString(itemsMap);
		cart.clear();
		// Giải mã chuỗi Base64
		byte[] encodedBytes = Base64.decodeBase64(cartValue.getBytes(StandardCharsets.UTF_8));
		String encodedCartValue = new String(encodedBytes, StandardCharsets.UTF_8);

		cookie = new Cookie("myCart", encodedCartValue);
		cookie.setMaxAge(86400);
		cookie.setPath("/");
		resp.addCookie(cookie);
		
		return "redirect:/devex/cart";
	}
	
}
