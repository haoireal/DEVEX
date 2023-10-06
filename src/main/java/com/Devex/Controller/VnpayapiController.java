package com.Devex.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Sevice.vnPayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class VnpayapiController {

	
	@Autowired
	vnPayService vnPayService;
	
	@GetMapping("/thanhtoanhoadon")
	public String index() {
		return "user/formOderpay";
	}

	
	 @PostMapping("/submitOrder")
	    public String submidOrder(@RequestParam("amount") String orderTotal1,
	                            @RequestParam("orderInfo") String orderInfo,
	                            HttpServletRequest request){
		 double orderTotal = Double.parseDouble(orderTotal1);

		// Hoặc chuyển đổi thành kiểu float
		float orderTotalFloat = Float.parseFloat(orderTotal1);

		// Nếu bạn muốn chuyển đổi thành kiểu int, bạn có thể sử dụng ép kiểu sau khi đã chuyển đổi thành kiểu double hoặc float.
		int orderTotalInt = (int) orderTotal; 
	        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	        System.out.println(baseUrl);
	        String vnpayUrl = vnPayService.createOrder(orderTotalInt, orderInfo, baseUrl);
	        System.out.println(vnpayUrl);
	        return "redirect:" + vnpayUrl;
	    }
	 
	 
	 @GetMapping("/vnpay-payment")
	    public String GetMapping(HttpServletRequest request, Model model){
	        int paymentStatus =vnPayService.orderReturn(request);
	        System.out.println("status: "+paymentStatus);
	        String orderInfo = request.getParameter("vnp_OrderInfo");
	        String paymentTime = request.getParameter("vnp_PayDate");
	        String transactionId = request.getParameter("vnp_TransactionNo");
	        String totalPrice = request.getParameter("vnp_Amount");

	        model.addAttribute("orderId", orderInfo);
	        model.addAttribute("totalPrice", totalPrice);
	        model.addAttribute("paymentTime", paymentTime);
	        model.addAttribute("transactionId", transactionId);

	        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	    }
	
	
}
