package com.Devex.Controller.admin;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.ShopDTO;
import com.Devex.DTO.StatisticalCategoryDetailsPieDTO;
import com.Devex.DTO.StatisticalOrderMonthPieDTO;
import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.DTO.UpdatedRolesDTO;
import com.Devex.Entity.Role;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

import jakarta.servlet.http.Cookie;


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
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private CartService cartService;

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
	    	System.out.println("sssss"+updatedRoles.getPassword());
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
	        
	        response.put("message", "Cập Nhật Roles Thành Công");
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
		Double amountRevenue= orderService.getTotalPriceOrder() * 0.05;
		int amountUser = userService.getAmountUserOfAdmin();
		long amountProduct = productService.count();
		mapStatistical.put("amountorder", amountorder);
		mapStatistical.put("amountRevenue", amountRevenue);
		mapStatistical.put("amountUser", amountUser);
		mapStatistical.put("amountProduct", amountProduct);
		return mapStatistical;
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/ad/revenue/line/month")
	public Map<String, Object> getRevenueByMonth(@RequestParam("year") int year, @RequestParam("month") int month){
		Map<String, Object> RevenueByMonth = new HashMap<>();
		User u = session.get("user");
		int yearCompare;
		int monthCompare;
		if(month == 1) {
			yearCompare = year - 1;
			monthCompare = 12;
		}else {
			yearCompare = year;
			monthCompare = month - 1;
		}
        List<Object[]> listt = orderService.getTotalPriceOrderByMonthAndYear(year, month);
        List<Object[]> listtc = orderService.getTotalPriceOrderByMonthAndYear(yearCompare, monthCompare);
        long amountCart = cartService.count(); 
        int amountOrderSuccess = orderService.getCountOrderByStatusIdAndYearAndMonth(1006, year, month);
        int huydon = orderService.getCountOrderFalseByStatusIdAndYearAndMonth(1007, year, month);
        int trahang = orderService.getCountOrderFalseByStatusIdAndYearAndMonth(1008, year, month);
        int amountOrderFalse = huydon + trahang;
        int amountOrderWaiting = orderService.getCountOrderWaitingByStatusIdAndYearAndMonth(year, month);
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		LocalDate date = LocalDate.of(year, month, 1);
		LocalDate dateCompare = LocalDate.of(yearCompare, monthCompare, 1);
        int lengthOfMonth = date.lengthOfMonth();
        int lengthOfMonthCompare = dateCompare.lengthOfMonth();
        if(lengthOfMonth > lengthOfMonthCompare) {
        	for (int i = 1; i <= lengthOfMonth; i++) {
                double price = 0;
                double priceCompare = 0;
                for (Object[] ob : listt) {
                    int day = (ob[0] == null) ? 0 : (int) ob[0];
                    if (day == i) {
                    	price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
                        break;
                    }
                }
                for (Object[] obc : listtc) {
                    int day = (obc[0] == null) ? 0 : (int) obc[0];
                    if (day == i) {
                    	priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
                        break;
                    }
                }
                StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
                statistical.setDay(i);
                statistical.setPrice(price);
                statistical.setPriceCompare(priceCompare);
                liststatis.add(statistical);
            }
        }else {
        	for (int i = 1; i <= lengthOfMonthCompare; i++) {
                double price = 0;
                double priceCompare = 0;
                for (Object[] ob : listt) {
                    int day = (ob[0] == null) ? 0 : (int) ob[0];
                    if (day == i) {
                    	price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
                        break;
                    }
                }
                for (Object[] obc : listtc) {
                    int day = (obc[0] == null) ? 0 : (int) obc[0];
                    if (day == i) {
                    	priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
                        break;
                    }
                }
                StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
                statistical.setDay(i);
                statistical.setPrice(price);
                statistical.setPriceCompare(priceCompare);
                liststatis.add(statistical);
            }
        }
        RevenueByMonth.put("liststatis", liststatis);
        RevenueByMonth.put("amountCart", amountCart);
        RevenueByMonth.put("amountOrderSuccess", amountOrderSuccess);
        RevenueByMonth.put("amountOrderWaiting", amountOrderWaiting);
        RevenueByMonth.put("amountOrderFalse", amountOrderFalse);
        return RevenueByMonth;
	}
	
	@GetMapping("/ad/revenue/line/year")
	public Map<String, Object> getRevenueByYear(@RequestParam("year") int year){
		User u = session.get("user");
		List<StatisticalRevenueMonthDTO> liststatis = new ArrayList<>();
		Map<String, Object> RevenueByMonth = new HashMap<>();
		List<Object[]> listt = orderService.getTotalPriceOrderByYear(year);
		List<Object[]> listtc = orderService.getTotalPriceOrderByYear(year-1);
		for (int i = 1; i <= 12; i++) {
			double price = 0;
            double priceCompare = 0;
			for (Object[] ob : listt) {
                int day = (ob[0] == null) ? 0 : (int) ob[0];
                if (day == i) {
                	price = (ob[1] == null) ? 0 : (double) ob[1] * 0.05;
                    break;
                }
            }
            for (Object[] obc : listtc) {
                int day = (obc[0] == null) ? 0 : (int) obc[0];
                if (day == i) {
                	priceCompare = (obc[1] == null) ? 0 : (double) obc[1] * 0.05;
                    break;
                }
            }
            StatisticalRevenueMonthDTO statistical = new StatisticalRevenueMonthDTO();
            statistical.setDay(i);
            statistical.setPrice(price);
            statistical.setPriceCompare(priceCompare);
            liststatis.add(statistical);
		}
		long amountCart = cartService.count(); 
        int amountOrderSuccess = orderService.getCountOrderByStatusIdAndYear(1006, year);
        int huydon = orderService.getCountOrderFalseByStatusIdAndYear(1007, year);
        int trahang = orderService.getCountOrderFalseByStatusIdAndYear(1008, year);
        int amountOrderFalse = huydon + trahang;
        int amountOrderWaiting = orderService.getCountOrderWaitingByStatusIdAndYear(year);
        RevenueByMonth.put("liststatis", liststatis);
        RevenueByMonth.put("amountCart", amountCart);
        RevenueByMonth.put("amountOrderSuccess", amountOrderSuccess);
        RevenueByMonth.put("amountOrderWaiting", amountOrderWaiting);
        RevenueByMonth.put("amountOrderFalse", amountOrderFalse);
        return RevenueByMonth;
	}
	
	@GetMapping("/ad/statistical/pie/year")
	public List<StatisticalCategoryDetailsPieDTO> getStatisticalCategoryDetails(@RequestParam("year") int year){
		List<Object[]> liststatiscategoryob = detailService.getTop5CategoryDetailsAndAmountProductSell(year);
		List<StatisticalCategoryDetailsPieDTO> liststatiscategory = new ArrayList<>();
		for (Object[] result : liststatiscategoryob) {
		    String categoryName = (String) result[0];
		    long productCount = (long) result[1];
		    StatisticalCategoryDetailsPieDTO entity = new StatisticalCategoryDetailsPieDTO();
		    entity.setName(categoryName);
		    entity.setCountProductSell(productCount);
		    liststatiscategory.add(entity);
		}
		return liststatiscategory;
	}
	
}	 
