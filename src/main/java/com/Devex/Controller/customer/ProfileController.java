package com.Devex.Controller.customer;

import java.security.Principal;
import java.util.Date;
import java.util.List;
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

import com.Devex.DTO.MailOtpDTO;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpValidationRequest;
import com.Devex.Entity.Customer;
import com.Devex.Entity.Notification;
import com.Devex.Entity.Role;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.NotificationService;
import com.Devex.Sevice.OTPService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

@Controller
public class ProfileController {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	private static final String PHONE_NUMBER_REGEX = "^(\\+84|0)[1-9]\\d{8}$";
	private static final String PHONE_NUMBER_PREFIX = "+84";

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	SellerService sellerService;

	@Autowired
	CustomerService customerService;

	@Autowired
	NotificationService notificationService;

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
	public String showProfile(Model model, Principal principal) {
		User user = session.get("user");
		String id = principal.getName();
		System.out.println(id);
		List<UserRole> role = userRoleService.findAllByUserName(user.getUsername());
		boolean flag = false;
		for (UserRole u : role) {
			if (u.getRole().getId().equals("SELLER")) {
				flag = true;
			}
		}
		model.addAttribute("role", flag);
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
		User user = session.get("user");
		Customer customer = customerService.findById(user.getUsername()).get();
		// Customer customer = session.get("user");
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
		if (type.equals("phone")) {
			info = user.getPhone();
			info = normalizePhoneNumber(info);
			OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
			otpService.sendSMS(otpRequest);
		} else if (type.equals("email")) {
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
			otp += param.getString("o" + i, "");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		boolean flag = false;

		if (type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);

		} else if (type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);

		}

		if (flag == false) {
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

		if (user != null) {
			model.addAttribute("message", "Email này đã tồn tại!");
			return "account/email-phone";
		}
		otpService.sendMailOtp(info);
		session.set("info-user", info);
		return "redirect:/profile/verify/new-email";
	}

	@PostMapping("/profile/change-phone")
	public String doUpdatePhone(Model model) {
		String info = param.getString("email-phone", session.get("info-user", ""));
		User user = user = userService.findPhone(info);
		;

		if (user != null) {
			model.addAttribute("message", "Số điện thoại này đã tồn tại!");
			return "account/email-phone";
		}
		info = normalizePhoneNumber(info);
		OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
		otpService.sendSMS(otpRequest);
		session.set("info-user", info);
		return "redirect:/profile/verify/new-phone";
	}

	@GetMapping("/profile/verify/new-{type}")
	public String showVerifyToUpdate(@PathVariable("type") String type) {

		return "account/verifi";
	}

	@PostMapping("/profile/verify/new-{type}")
	public String doVerifyToUpdate(@PathVariable("type") String type, Model model) {
		User user = session.get("user");
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i, "");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		boolean flag = false;

		if (type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);
			if (flag) {
				user.setEmail(infoUser);
				userService.save(user);
			}
		} else if (type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);
			if (flag) {
				infoUser = "0" + infoUser.substring(3);
				user.setPhone(infoUser);
				userService.save(user);
			}
		}
		if (flag == false) {
			model.addAttribute("message", "Mã xác thực không trùng khớp!");
			return "account/verifi";
		}
		return "redirect:/profile";
	}

	@PostMapping("/upgrade-seller")
	public String upgradeSeller(Model model) {
		String shopName = param.getString("shopName", "");
		String description = param.getString("description", "");
		User user = session.get("user");
		System.out.println(user.getUsername());
		Seller seller = new Seller();
		// thông tin user gốc
		seller.setUsername(user.getUsername());
		seller.setActive(user.getActive());
		seller.setAvatar(user.getAvatar());
		seller.setCreateDay(user.getCreateDay());
		seller.setEmail(user.getEmail());
		seller.setFullname(user.getFullname());
		seller.setGender(user.getGender());
		seller.setPhone(user.getPhone());
		seller.setPassword(user.getPassword());

		// thông tin entity Seller
		seller.setShopName(shopName);
		seller.setAddress(null);
		seller.setPhoneAddress(null);
		seller.setActiveShop(true);
		seller.setMall(false);
		seller.setDescription(description);
		// save
		sellerService.insertSeller(user.getUsername(), shopName, null, null, false, true, description);
		// thêm quyền seller
		UserRole userRole = new UserRole();
		Role role = roleService.findById("SELLER").orElse(null);
		userRole.setRole(role);
		userRole.setUser(user);
		userRoleService.save(userRole);
		// Thông báo cho admin
		Notification noti = new Notification();
		noti.setName("Nâng cấp lên nhà bán hàng");
		noti.setCreatedDay(new Date());
		noti.setContent("Tài khoản người dùng " + user.getUsername() + " vừa trở thành nhà bán.\n"
				+ "Thông tin tên shop: " + shopName + "\n"
				+ "Mô tả: " + description);
		User admin = userService.findById("haopg").get();
		noti.setUser(admin);
		notificationService.save(noti);

		String message = "Nâng cấp nhà bán thành công";
		model.addAttribute("message", message);
		return "user/profile";
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
