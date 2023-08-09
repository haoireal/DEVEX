package com.Devex.Controller.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.OTPService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import lombok.extern.slf4j.Slf4j;

import com.Devex.DTO.MailOtpDTO;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpValidationRequest;
import com.Devex.Entity.Customer;
import com.Devex.Entity.User;

@Controller
public class ProfileController {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+84|0)[1-9]\\d{8}$";
    private static final String PHONE_NUMBER_PREFIX = "+84";
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
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
	
	@GetMapping("/profile")
	public String showProfile() {
		
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String doEditProfile(@RequestParam("avatarInput") MultipartFile file) {
		User user = session.get("user");
		String fullname = param.getString("fullname", "");
		String gender = param.getString("gender", "Other");
		String avatar = param.getString("avatar", null);
		param.save(file, "/img/account");
		user.setGender(gender);
		user.setFullname(fullname);
		user.setAvatar(avatar);
		userService.save(user);
		return "redirect:/profile";
	}
	
	@PostMapping("/profile/address")
	public String doEdiAddress() {
		Customer customer = session.get("user");
		String address = param.getString("address", null);
		String phone = param.getString("phone", null);
		customer.setAddress(address);
		customer.setPhoneAddress(phone);
		customerService.save(customer);
		return "redirect:/profile";
	}
	
	@GetMapping("/profile/verify/{type}")
	public String showVerify(@PathVariable("type") String type) {
		User user = session.get("user");
		String info = null;
		if(type.equals("phone")) {
			info = user.getPhone();
			info = normalizePhoneNumber(info);
			OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
			otpService.sendSMS(otpRequest);
		}else if(type.equals("email")) {
			info = user.getEmail();
			otpService.sendMailOtp(info);
		}
		session.set("info-user", info);
		return "account/verifi";
	}
	
	@PostMapping("/profile/verify/{type}")
	public String showVerifyForm(@PathVariable("type") String type, Model model) {
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i,"");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
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
		
		String redirectUrl = String.format("redirect:/profile/change-%s", type);
        return redirectUrl;
	}
	
	@GetMapping("/profile/change-email")
	public String showUpdateEmail(Model model) {
		model.addAttribute("type", "email");
		return "account/email-phone";
	}
	
	@GetMapping("/profile/change-phone")
	public String showUpdatePhone(Model model) {
		model.addAttribute("type", "phone");
		return "account/email-phone";
	}
	
	@PostMapping("/profile/change-email")
	public String doUpdateEmail(Model model) {
		String info = param.getString("email-phone", session.get("info-user", ""));
		User user = user = userService.findEmail(info);
		
		if(user != null) {
			model.addAttribute("message", "Email này đã tồn tại!");
        	return "account/email-phone";
		}
		otpService.sendMailOtp(info);
        session.set("info-user", info);
		return "redirect:/profile//verify/new-email";
	}
	
	@PostMapping("/profile/change-phone")
	public String doUpdatePhone(Model model) {
		String info = param.getString("email-phone", session.get("info-user", ""));
		User user = user = userService.findPhone(info);;
		
		if(user != null) {
			model.addAttribute("message", "Số điện thoại này đã tồn tại!");
        	return "account/email-phone";
		}
		info = normalizePhoneNumber(info);
		OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
		otpService.sendSMS(otpRequest);
        session.set("info-user", info);
		return "redirect:/profile//verify/new-phone";
	}
	
	@GetMapping("/profile/verify/new-{type}")
	public String showVerifyToUpdate(@PathVariable("type") String type) {
		
		return	"account/verifi";
	}
	
	@PostMapping("/profile/verify/new-{type}")
	public String doVerifyToUpdate(@PathVariable("type") String type, Model model) {
		User user = session.get("user");
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i,"");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		boolean flag = false;
		
		if(type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);
			if(flag) {
				user.setEmail(infoUser);
				userService.save(user);
			}
		}else if(type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);
			if(flag) {
				infoUser = "0" + infoUser.substring(3);
				user.setPhone(infoUser);
				userService.save(user);
			}
		}
		if(flag == false) {
			model.addAttribute("message", "Mã xác thực không trùng khớp!");
			return "account/verifi";
		}
		return	"redirect:/profile";
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
