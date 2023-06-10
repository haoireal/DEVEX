package com.hnbcoffee.Controller.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Repository.BeverageCategoryRepository;
import com.hnbcoffee.Sevice.BeverageService;
import com.hnbcoffee.Sevice.ParamService;
import com.hnbcoffee.Sevice.SessionService;
import com.hnbcoffee.Sevice.UserService;

@Controller
@RequestMapping("/hnbcoffee/admin")
public class AdminAccountController {
	@Autowired
	BeverageService beverageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BeverageCategoryRepository beverageCategoryRepository;
		
	@Autowired
	SessionService sessionService;
	
	@Autowired
	ParamService paramService;
	
	@GetMapping("/account")
    public String getAdminAccount(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("user", new User());
		model.addAttribute("users", list);
		return "admin/account";
    }
	
	@RequestMapping("/account/role")
	public String updateRole(Model model) {
		User u = userService.findByEmailLike(paramService.getString("email", ""));
		u.setRole(paramService.getString("role", ""));
		userService.save(u); //Size S
		return "redirect:/hnbcoffee/admin/account";
	}
	
	@RequestMapping("/account/active")
	public String updateActive(Model model) {
		User u = userService.findByEmailLike(paramService.getString("email", ""));
		u.setAcive(paramService.getBoolean("active", false));
		userService.save(u); //Size S
		return "redirect:/hnbcoffee/admin/account";
	}
	@RequestMapping("/account/update/{id}")
	public String update(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		String fullname = paramService.getString("fullname", "");
		String email = paramService.getString("email", "");
		String password = paramService.getString("password", "");
		String address = paramService.getString("address", "");
		System.out.println(userService.findById(id).orElse(null).getBirthday());
		Date birthday = paramService.getDate("birthday", "yyyy-MM-dd");
		System.out.println(birthday.toString());

		String gender = paramService.getString("gender", "");
		User u = new User();
		u.setId(id); System.out.println(id);
		u.setFullname(fullname);
		u.setEmail(email);
		u.setPassword(password);
		u.setAddress(address);
		u.setBirthday(birthday);
		u.setGender(((gender.equalsIgnoreCase("true")) ? true : false));
		u.setBirthday(birthday);
		u.setAcive(true);
		userService.save(u);
		return "redirect:/hnbcoffee/admin/account";
	}
	
	@RequestMapping("/account/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			//xoá toàn bộ đơn hàng của user này
			// insert code 
			userService.deleteById(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Dữ liệu bạn xoá có ảnh hưởng tới các dữ liệu khác");
		}
		return "redirect:/hnbcoffee/admin/account";
	}
	
}
