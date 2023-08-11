package com.Devex.Controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.UpdatedRolesDTO;
import com.Devex.Entity.Role;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;


@CrossOrigin("*")
@RestController

public class DevexAdminRestController {


	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SessionService session;
	
	

	
	@GetMapping("/api/userDetail")
	public Map<String, Object> updateUser() {
	
		String username = session.get("username");
		Map<String , Object> userDetails = new HashMap<>();
		userDetails.put("userRoles", userRoleService.findAllByUserName(username));
		userDetails.put("roles", roleService.findAll());
		return userDetails;
		
	}
	
	
	@PostMapping("/api/updateUserRoles")
	public ResponseEntity<Map<String, String>> updateUserRoles(@RequestBody UpdatedRolesDTO updatedRoles) {
	    Map<String, String> response = new HashMap<>();
	    User user = userService.findById(updatedRoles.getUserId()).orElse(null);
	    if (user != null) {
	    	
	    	List<UserRole> userRolesToDelete = userRoleService.findAllByUserName(updatedRoles.getUserId());


	        // Xoá tất cả user roles liên quan đến user dựa trên username
	        for (UserRole userRole : userRolesToDelete) {
	            userRoleService.delete(userRole);
	        }
	        
	        // Thêm mới user roles với các role đã chọn
	        for (String roleId : updatedRoles.getRoles()) {
	            Role role = roleService.findById(roleId).orElse(null);
	            if (role != null) {
	                UserRole newUserRole = new UserRole();
	                newUserRole.setUser(user);
	                newUserRole.setRole(role);
	                userRoleService.save(newUserRole); // Lưu userRole mới
	            }
	        }
	        response.put("message", "Roles updated successfully");
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("error", "User not found");
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	
}	 
