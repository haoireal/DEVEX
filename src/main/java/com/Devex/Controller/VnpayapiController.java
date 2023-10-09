package com.Devex.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.User;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.vnPayService;
import com.Devex.Sevice.ServiceImpl.MailerServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class VnpayapiController {

	
	@Autowired
	vnPayService vnPayService;
	
	@Autowired
	MailerServiceImpl mailer;
	@Autowired
    SessionService session;
	
	@GetMapping("/thanhtoanhoadon")
	public String index() {
		return "user/formOderpay";
	}

	
	 @PostMapping("/submitOrder")
	    public String submidOrder(@RequestParam("amount") String orderTotal1,
	                            @RequestParam("orderInfo") String orderInfo,
	                            @RequestParam(value="product_name",required = false ,defaultValue = "Không có") List<String> product_name,
	                            @RequestParam(value="product_size",required = false,defaultValue = "Không có") List<String> product_size,
	                            @RequestParam(value="product_color",required = false,defaultValue = "Không có") List<String> product_color,
	                            @RequestParam(value="product_price",required = false,defaultValue = "Không có") List<String> product_price,
	                            @RequestParam(value="product_quantity",required = false,defaultValue = "Không có") List<String> quantity,
	                            @RequestParam(value="product_img",required = false,defaultValue = "Không có") List<String> product_img,
	                            HttpServletRequest request){
		
		
		 double orderTotal = Double.parseDouble(orderTotal1);
		 float orderTotalFloat = Float.parseFloat(orderTotal1);
		 int orderTotalInt = (int) orderTotal; 
	     String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	     String vnpayUrl = vnPayService.createOrder(orderTotalInt, orderInfo, baseUrl);   
//	     User user = session.get("user");
//	     
//	     String img_logo = "";
	     
	      try {
	    	  StringBuilder emailContentBuilder = new StringBuilder();
	    	  emailContentBuilder.append("Chào bạn,\n\n");
	    	  emailContentBuilder.append("Cảm ơn bạn đã đặt đơn hàng của chúng tôi\n");
	    	  emailContentBuilder.append("Dưới đây là thông tin đơn hàng của bạn:\n");
				
				 int size2 = product_name.size(); for (int i = 0; i < size2; i++) {
				  emailContentBuilder.append("- Tên sản phẩm: ").append(product_name.get(i)).
				  append("\n");
				 emailContentBuilder.append("- Kích thước: ").append(product_size.get(i)).
				 append("\n");
				 emailContentBuilder.append("- Màu sắc: ").append(product_color.get(i)).append
				 ("\n");
				  emailContentBuilder.append("- Giá sản phẩm: ").append(product_price.get(i)).
				  append("\n");
				  emailContentBuilder.append("- Số lượng: ").append(quantity.get(i)).append(
				  "\n"); emailContentBuilder.append("------------------------\n"); }
				 

	    	  emailContentBuilder.append("Cảm ơn bạn đã mua hàng của chúng tôi. Hãy liên hệ chúng tôi nếu bạn có bất kỳ câu hỏi hoặc yêu cầu nào.\n\n");
	    	  emailContentBuilder.append("Trân trọng,\n");
	    	  emailContentBuilder.append("Đội ngũ dịch vụ khách hàng của chúng tôi");

	    	  String emailContent = emailContentBuilder.toString();
	    	  
			mailer.send("trank1793@gmail.com", "DEVEX - THÔNG BÁO XÁC NHẬN ĐƠN HÀNG CỦA BẠN", emailContent);
		} catch (Exception e) {
			System.out.println("Lỗi");
			System.out.println(e);
		}
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

	        return paymentStatus == 1 ? "user/paymentSuccess" : "orderfail";
	    }
	 
	 
	 
	 
	
	
}
