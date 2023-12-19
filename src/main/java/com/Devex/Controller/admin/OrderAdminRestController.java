package com.Devex.Controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Entity.Customer;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderStatus;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.OrderStatusService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class OrderAdminRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list/order")
	public Map<String, Object> getListOrder(@RequestParam("status") int status, @RequestParam("search") String search){
		Map<String, Object> mapOrders = new HashMap<>();
		List<Order> listOrder = new ArrayList<>();
		if(status == 1 && search.equals("undefined") || status == 1 && search.equals("")) {
			listOrder = orderService.findAllOrderSortDown();
			System.out.println("Tìm tất cả");
		}else if(status == 1 && !search.equals("undefined")) {
			listOrder = orderService.findOrderByIdOrCustomer(search);
			System.out.println("Tìm tất cả theo keyword");
		}else if(status != 1 && search.equals("undefined") || status != 1 && search.equals("")){
			listOrder = orderService.findOrderByOrderStatusId(status);
			System.out.println("Tìm theo trạng thái");
		}else if(status != 1 && !search.equals("undefined")) {
			listOrder = orderService.findOrderByOrderStatusIdAndIdOrCustomer(status, search);
			System.out.println("Tìm trạng thái và keyword");
		}
		List<String> listCustomer = new ArrayList<>();
		List<String> listPayment = new ArrayList<>();
		for (Order o : listOrder) {
			Customer c = customerService.findById(o.getCustomerOrder().getUsername()).get();
			listCustomer.add(c.getFullname());
			listPayment.add(o.getPayment().getName());
		}
		mapOrders.put("listOrder", listOrder);
		mapOrders.put("listCustomer", listCustomer);
		mapOrders.put("listPayment", listPayment);
		return mapOrders;
	}
	
	@GetMapping("/list/status")
	public List<OrderStatus> getListStatus(){
		List<OrderStatus> listStatus = new ArrayList<>();
		List<OrderStatus> listt = orderStatusService.getListOrderStatusAdmin();
		listStatus.add(new OrderStatus(1, "Tất cả", null));
		for (OrderStatus o : listt) {
			listStatus.add(o);
		}
		return listStatus;
	}
	
}
