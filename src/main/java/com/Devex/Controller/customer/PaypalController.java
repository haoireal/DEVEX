package com.Devex.Controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Sevice.PaypalService;
import com.Devex.Sevice.SessionService;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {
	@Autowired
	PaypalService paypalService;
	
	@Autowired
	SessionService session;

	@PostMapping("/paypal-payment")
	public String authorizePayment(Model model) {

        List<CartDetailDTo> list = session.get("listItemOrder", null);
        System.out.println(list);
        try {
            String approvalLink = paypalService.authorizePayment(list);
            
            return "redirect:" + approvalLink;
             
        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            return "admin/erorr404";
        }
		
	}
}
