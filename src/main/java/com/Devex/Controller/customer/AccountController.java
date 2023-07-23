package com.Devex.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Devex.DTO.MailOtpDTO;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpValidationRequest;
import com.Devex.Entity.Role;
import com.Devex.Entity.User;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.OTPService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
public class AccountController {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+84|0)[1-9]\\d{8}$";
    private static final String PHONE_NUMBER_PREFIX = "+84";
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	MailerService emailService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	
	@GetMapping("/signup")
	public String showSignup() {
		return "account/email-phone";
	}
	
	@PostMapping("/signup")
	public String sendSmsSignup(Model model) {
		String info = param.getString("email-phone", session.get("info-user", ""));
		User user = null;
		String type = null;
		if (isValidEmail(info)) {
            type = "email";
            user = userService.findEmail(info);
        }else if (isValidPhoneNumber(info)) {
        	user = userService.findPhone(info);
            // Chuyển đổi định dạng số điện thoại về "+84" nếu cần
            info = normalizePhoneNumber(info);
            type = "phone";
        }
		
		if(user != null) {
			model.addAttribute("message", "Email hoặc số điện thoại này đã tồn tại!");
        	return "account/email-phone";
		}
		if(type.equals("phone")) {
			OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
			otpService.sendSMS(otpRequest);
		}else if(type.equals("email")) {
			otpService.sendMailOtp(info);
		}else {
        	model.addAttribute("message", "Vui lòng nhập đúng định dạng email hoặc số điện thoại");
        	return "account/email-phone";
        }
        session.set("info-user", info);
		return "redirect:/verify";
	}
	
	@GetMapping("/signup-information")
	public String showInfSignup() {
		return "account/signup";
	}
	
	@PostMapping("/signup-information")
	public String doInfSignup() {
		User user = new User();
		String username = param.getString("username", "");
		String fullname = param.getString("fullname", "");
		String password = param.getString("password", "");
		String gender = param.getString("gender", "Other");
		boolean active = true;
		Role role = roleService.findById(101).orElse(null);
		Date createdDate = new Date();
		String email = null;
		String phone = null;
		String info = session.get("info-user", "");
		if (isValidEmail(info)) {
            email = info;
        }else if (isValidPhoneNumber(info)) {
            // Chuyển đổi định dạng số điện thoại về "0.."
        	info = "0" + info.substring(3);

            phone = info;
        }
		user.setUsername(username);
		user.setFullname(fullname);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword(password);
		user.setCreateDay(createdDate);
		user.setGender(gender);
		user.setActive(active);
		user.setAvatar(null);
		user.setRole(role);
		
		userService.save(user);
		
		return "redirect:/signin";
	}
	
	@GetMapping("/verify")
	public String showVerify() {
		return "account/verifi";
	}
	
	
	@PostMapping("/verify")
	public String doVerify(Model model) {
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i,"");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		String type = null;
		if (isValidEmail(infoUser)) {
            type = "email";
        }else {
            type = "phone";
        }
		boolean flag = false;
		
		if(type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);
		}else if(type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);
		}
		
		if(flag == false) {
			model.addAttribute("message", "Mã xác thực không trùng khớp!");
			return "account/verifi";
		}
		
		return "redirect:/signup-information";
	}
	
	@GetMapping("/verify-password")
	public String showVerifyPassword() {
		return "account/current-password";
	}
	
	@PostMapping("/verify-password")
	public String doVerifyPassword(Model model) {
		User user = session.get("user");
		String pass = param.getString("password", "");
		if(!user.getPassword().equals(pass)) {
			model.addAttribute("message", "Mật khẩu không trùng khớp!");
			return "account/current-password";
		}
		return "redirect:/change-password";
	}
	
	@GetMapping("/change-password")
	public String showChangePassword() {
		
		return "account/new-password";
	}
	
	@PostMapping("/change-password")
	public String doChangePassword(Model model) {
		User user = session.get("user");
		String pass = param.getString("password", "");
		String confirmPass = param.getString("confirm-password", "");
		if(!confirmPass.equals(pass)) {
			model.addAttribute("message", "Xác thực mật khẩu không trùng khớp!");
			return "account/new-password";
		}
		user.setPassword(confirmPass);
		userService.save(user);
		return "redirect:/profile";
	}
	

	
	@GetMapping("/forget-password")
	public String showForgetPassword() {
		return "account/new-password";
	}
	
	private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
	


    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private static String normalizePhoneNumber(String phoneNumber) {
        // Nếu số điện thoại không bắt đầu bằng "+84", chuyển về "+84"
        if (!phoneNumber.startsWith(PHONE_NUMBER_PREFIX)) {
            phoneNumber = PHONE_NUMBER_PREFIX + phoneNumber.substring(1);
        }
        return phoneNumber;
    }
    

}
