package com.Devex.Controller.admin;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.User;
import com.Devex.Sevice.UserService;

import jakarta.annotation.Resource;



@CrossOrigin("*")
@RestController
//@Controller

public class DevexAdminUserController {
	
	@Autowired
	UserService userService;
	
	
	
	

	@GetMapping("/admin/userManage1")
	public List<User> getUserManage(ModelMap model ) {
		List<User> listAllUser = userService.findAll();
		return listAllUser ; 
	}


	
	
//	@RequestMapping("/admin/user/edit/{username}")
//	public String editUser(@PathVariable("username") String username, ModelMap model) {	
//		User userdetail = userService.findById(username).orElse(new User());		
//		model.addAttribute("userdetail", userdetail);
//		
//		return "admin/userManage/formUserManage";
//	}
	
//	@RequestMapping(value = "/user/update", method = RequestMethod.PUT)
//  public String updateUser(@ModelAttribute User user, ModelMap model) throws IllegalAccessException, InvocationTargetException {
//       User currentUser = userService.findById(user.getUsername()).get();
//        BeanUtils.copyProperties(user, currentUser);
//       userService.save(currentUser);
//        model.addAttribute("user", currentUser);
//       return "admin/userManage/formUserManage";
//    }
//	
	
}
