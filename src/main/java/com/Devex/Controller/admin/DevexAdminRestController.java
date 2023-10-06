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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.ShopDTO;
import com.Devex.DTO.UpdatedRolesDTO;
import com.Devex.Entity.Role;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DevexAdminRestController {


	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService session;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;

	@GetMapping("/userDetail")
	public Map<String, Object> updateUser() {
	
		String username = session.get("username");
		Map<String , Object> userDetails = new HashMap<>();
		userDetails.put("userRoles", userRoleService.findAllByUserName(username));
		userDetails.put("roles", roleService.findAll());
		return userDetails;
		
	}
	
	

	@PostMapping("/updateUserRoles")
	public ResponseEntity<Map<String, String>> updateUserRoles(@RequestBody UpdatedRolesDTO updatedRoles) {
	    Map<String, String> response = new HashMap<>();
	    User user = userService.findById(updatedRoles.getUserId()).orElse(null);
	    Seller seller = sellerService.findFirstByUsername(user.getUsername());
	    
    
	    if (user != null) {    	
	    	// update user trước khi tạo roles
	    	 userService.updateUser(updatedRoles.getFullname(), updatedRoles.getEmail(), updatedRoles.getPassword(), updatedRoles.getPhone(), updatedRoles.getGender(), updatedRoles.isActive(), updatedRoles.getUserId());
	    	
	    	
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

	                // tạo mới seller
	                if("SELLER".equals(roleId)) {
	                	if(seller == null) {
	                		sellerService.insertSeller(user.getUsername(),"Default" ,"Default","Default", false, true, null);    
	                	}
	                }
	              
	            }// END IF ROLE

	        }// end for
	        
	        response.put("message", "Cập Nhập Roles Thành Công");
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("error", "User not found");
	        return ResponseEntity.badRequest().body(response);
	    }
	}

	//admin rest seller API
	@GetMapping("/shop")
	public Map<String, Object> getSeller(){
		String userSeller =   session.get("userSeller");
//		System.out.println("sssss"+userSeller);
		Map<String , Object> sellerDetails = new HashMap<>();
		sellerDetails.put("seller", sellerService.findFirstByUsername(userSeller));
		
		return sellerDetails;
	}
	
	@PostMapping("/updateShop")
    public ResponseEntity<Map<String, String>> saveSellerProfile(@RequestBody ShopDTO ShopDTO) {
        // Lấy thông tin người dùng từ session hoặc nguồn dữ liệu khác
        User user = session.get("user");
        Seller selleru = sellerService.findFirstByUsername(user.getUsername());
        String message = "Cập Nhập Thông Tin Thất Bại";   
        // Xử lý dữ liệu và lưu vào cơ sở dữ liệu
        if (selleru != null) {
        	sellerService.updateSeller(ShopDTO.getShopName(), ShopDTO.getAddress(), ShopDTO.getPhoneAddress(), ShopDTO.getMall(), true, ShopDTO.getDescription(), user.getUsername());
            message = "Cập Nhập Thông Tin Thành Công";
        } 

        // Chuẩn bị kết quả trả về dưới dạng JSON
        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/admin/revenue")
	public Map<String, Object> getRevenueAdmin(){
		Map<String, Object> mapStatistical = new HashMap<>();
		long amountorder = orderService.count();
		Double amountRevenue= orderService.getTotalPriceOrder();
		int amountUser = userService.getAmountUserOfAdmin();
		long amountProduct = productService.count();
		mapStatistical.put("amountorder", amountorder);
		mapStatistical.put("amountRevenue", amountRevenue);
		mapStatistical.put("amountUser", amountUser);
		mapStatistical.put("amountProduct", amountProduct);
		return mapStatistical;
	}
	
}	 
