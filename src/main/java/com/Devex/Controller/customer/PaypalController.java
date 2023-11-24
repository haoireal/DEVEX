package com.Devex.Controller.customer;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.User;
import com.Devex.Sevice.PaypalService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ServiceImpl.MailerServiceImpl;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {
	@Autowired
	PaypalService paypalService;
	
	@Autowired
	SessionService session;
	@Autowired
	MailerServiceImpl mailer;
	
	@PostMapping("/paypal-payment")
	public String authorizePayment(Model model) {
	    User user = session.get("user");
        List<CartDetailDTo> list = session.get("listItemOrder", null);
        System.out.println(list.get(0).getPrice());
        try {
            String approvalLink = paypalService.authorizePayment(list);
            session.set("payment", "paypal");
            //Gửi Email 
            try {
		    	  StringBuilder emailContentBuilder = new StringBuilder();
		    	  emailContentBuilder.append("  <table border=\"0\" style=\"margin: 0 auto; font-family: Arial, Helvetica, sans-serif;\" >");
		    	  emailContentBuilder.append("<tr style=\"background-color: rgb(254, 253, 253); width: 500px; height: 100px; outline: 1px solid rgb(180, 158, 158); display: flex; justify-content: center; align-items: center;\">\r\n"
		    	  		+ "            <td><img src=\"https://i.pinimg.com/564x/3a/4c/47/3a4c478ee0994afd0a983082ca14870c.jpg\" style=\"margin-top: 15px;margin-left: 170px;\" width=\"150px\" ></td>\r\n"
		    	  		+ "        </tr>");
		    	  emailContentBuilder.append(" <tr style=\"background-color: rgb(254, 253, 253); width: 500px; height: 500px; outline: 1px solid rgb(180, 158, 158);\">");
		    	  emailContentBuilder.append("<td>\r\n"
		    	  		+ "                <div style=\"width: 400px; margin: auto;\">");
		    	  emailContentBuilder.append("<p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(42, 68, 119); font-size: 18px;\">THÔNG TIN ĐƠN HÀNG SỐ</p>");
		    	  emailContentBuilder.append("<p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(80, 114, 161);\">1. Thông Người Đặt Hàng</p>");
		    	  emailContentBuilder.append("<p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(80, 114, 161); font-size: 15px;\">Họ Và Tên :" +user.getFullname()+"</p>");
		    	  emailContentBuilder.append("<hr>\r\n"
		    	  		+ "                    <p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(80, 114, 161); font-size: 15px;\">Số Điện Thoại :"+user.getPhone()+"</p>");
		    	  emailContentBuilder.append("<hr>\r\n"
		    	  		+ "                    <p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(80, 114, 161); font-size: 15px;\">Địa Chỉ :"+""+"</p>");
		    	  emailContentBuilder.append("<hr>\r\n"
		    	  		+ "                    <p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(80, 114, 161); font-size: 15px;\"> Mô Tả :"+"</p>");
		    	  emailContentBuilder.append("<hr>\r\n"
		    	  		+ "                    <p style=\"font-family: Arial, Helvetica, sans-serif; font-weight: 600; color: rgb(51, 113, 200);\">2.Sản Phẩm Đặt Hàng</p>");
		    	  emailContentBuilder.append(" <table style=\"margin: 0 auto; border-collapse: collapse; width: 100%; text-align: center; font-size: 15px;\" >");
		    	  emailContentBuilder.append("<tr>\r\n"
		    	  		+ "                            <td style=\"border: 2px solid rgb(132, 177, 225);\">STT</td>\r\n"
		    	  		+ "                            <td style=\"border: 2px solid  rgb(132, 177, 225);\">Tên Sản Phẩm</td>\r\n"
		    	  		+ "                            <td style=\"border: 2px solid  rgb(132, 177, 225);\">Số Lượng</td>\r\n"
		    	  		+ "                            <td style=\"border: 2px solid  rgb(132, 177, 225);\">Giá</td>\r\n"
		    	  		+ "                            <td style=\"border: 2px solid  rgb(132, 177, 225);\">Tổng</td>\r\n"
		    	  		+ "                        </tr>");
		    	  
		    	  double tong2= 0;
		    	  DecimalFormat decimalFormat = new DecimalFormat("###,###");
		    	  for (CartDetailDTo cartDetailDTo : list) {
					if(cartDetailDTo.getColor().equals("")) {
						cartDetailDTo.setColor("Không có");
					}
					if(cartDetailDTo.getSize().equals("")) {
						cartDetailDTo.setSize("Không có");
					}
					double tong = cartDetailDTo.getPrice() * cartDetailDTo.getQuantity();
					
					double gia2 = cartDetailDTo.getPrice();
					String formattedNumber = String.format("%,d", (int) gia2);

					String formattedNumber2 = String.format("%,d", (int) tong);
					
					 emailContentBuilder.append("<tr>");
		    		  emailContentBuilder.append(" <td style=\"border: 2px solid  rgb(132, 177, 225);\">"+"</td>");
			    	  emailContentBuilder.append("<td style=\"border: 2px solid  rgb(132, 177, 225);\">"+"<p>"+cartDetailDTo.getNameProduct()+"</p>\r\n"
			    	  		+ "                                <p>Màu Sắc :"+cartDetailDTo.getColor()+"</p>\r\n"
			    	  		+ "                                <p>Size :"+cartDetailDTo.getSize()+"</p>"+  "</td>");
			    	  emailContentBuilder.append("<td style=\"border: 2px solid  rgb(132, 177, 225);\">"+cartDetailDTo.getQuantity()+"</td>");
			    	  emailContentBuilder.append("<td style=\"border: 2px solid  rgb(132, 177, 225);\">"+formattedNumber+"</td>");
			    	  emailContentBuilder.append("<td style=\"border: 2px solid  rgb(132, 177, 225);\">"+formattedNumber2+"</td>");
			    	  emailContentBuilder.append("</tr>");
			    	  tong2 += tong;
				}
		    	  String formattedNumber = String.format("%,d", (int) tong2);
		    	  emailContentBuilder.append("</table>\r\n"
		    	  		+ "                    <hr>");
		    	  emailContentBuilder.append("<p style=\"font-weight: 400;\">Giảm Giá:  <a href=\"\" style=\"color: rgb(51, 51, 51); font-weight: bold;font-size: 15px; float: right;text-decoration: none; \">o VND</a></p> \r\n"
		    	  		+ "                    <p style=\"font-weight: 400;\">Phí Vận Chuyển :  <a href=\"\" style=\"color: rgb(51, 51, 51); font-weight: bold;font-size: 15px; float: right;text-decoration: none; \">10.0000 VND</a></p>\r\n"
		    	  		+ "                    <p style=\"font-weight: bold;\">Tổng Tiền :  <a href=\"\" style=\"color: rgb(254, 3, 3); font-weight: bold;font-size: 16px; float: right;text-decoration: none; \">"+formattedNumber+"VND"
		    	  		+ "                </a></p> </div>");
		    	  emailContentBuilder.append("   </td>\r\n"
		    	  		+ "           \r\n"
		    	  		+ "        </tr>\r\n"
		    	  		+ "        <tr style=\"background-color: rgb(104, 170, 228); width: 500px; height: 100px; outline: 1px solid rgb(180, 158, 158);; margin: 0 auto; display: flex; justify-content: center; align-items: center; color: white;\">\r\n"
		    	  		+ "            <td style=\"font-size: 15px;\">\r\n"
		    	  		+ "                <p> Truy Cập Trang Web Để Nhập Ưu Đãi</p>\r\n"
		    	  		+ "                <p>Cảm Ơn Bạn Đã Tin Tưởng DEVEX</p>\r\n"
		    	  		+ "            </td>\r\n"
		    	  		+ "            \r\n"
		    	  		+ "        </tr>\r\n"
		    	  		+ "    </table>");
		    	

		    	  String emailContent = emailContentBuilder.toString();
		    	  
				mailer.send("trank1793@gmail.com", "DEVEX - THÔNG BÁO XÁC NHẬN ĐƠN HÀNG CỦA BẠN", emailContent);
			} catch (Exception e) {
				System.out.println("Lỗi");
				System.out.println(e);
			}
            
            
            return "redirect:" + approvalLink;
             
        } catch (PayPalRESTException ex) {
            ex.printStackTrace();
            return "admin/erorr404";
        }
		
	}
}
