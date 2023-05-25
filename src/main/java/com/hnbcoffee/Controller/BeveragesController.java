package com.hnbcoffee.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Utils.DataSharing;

@Controller
@RequestMapping("/hnbcoffee")
public class BeveragesController {

	@GetMapping("/coffee")
	public String coffeeController(Model model) {
		model.addAttribute("beverages", DataSharing.items.values());
		return "user/coffee";
	}
}
