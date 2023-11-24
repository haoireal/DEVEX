package com.Devex.Controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
	@GetMapping("/message")
	public String showMessage(Model model) {
		return "user/message";
	}
}
