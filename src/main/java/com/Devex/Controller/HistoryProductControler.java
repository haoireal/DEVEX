package com.Devex.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.History;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Sevice.HistoryService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SessionService;

@Controller
public class HistoryProductControler {

	@Autowired
	HistoryService historyService;
	@Autowired
	ProductService productService;
	@Autowired
    SessionService session;
	@RequestMapping("/history")
	public String home(Model model) {
		User user = session.get("user");
		String username =user.getUsername();
		if(user!=null) {
			List<Product> sp = new ArrayList<>();
			List<History> history = historyService.findByIdUser(user);
			for (History history2 : history) {
				Optional<Product> product = productService.findById(history2.getProductId());
				if (product.isPresent()) {
					Product product2 = product.get();
					sp.add(product2);
				}
			}
			model.addAttribute("products", sp);
		}
		

		return "user/historyProduct";
	}

}
