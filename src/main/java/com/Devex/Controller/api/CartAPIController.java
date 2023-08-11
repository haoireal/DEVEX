package com.Devex.Controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Customer;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.ProductVariant;
import com.Devex.Repository.CartDetailRespository;
import com.Devex.Sevice.CartDetailService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.OrderStatusService;
import com.Devex.Sevice.PaymentService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SessionService;

@CrossOrigin("*")
@RestController
public class CartAPIController {
	@Autowired
	CartDetailService cart;

	@Autowired
	SessionService sessionService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	OrderStatusService orderStatusService;

	@Autowired
	ProductVariantService productVariantService;

	@GetMapping("/rest/cart")
	public List<CartDetailDTo> getAll(Model model) {
		Customer user = sessionService.get("user");
		List<CartDetailDTo> cartDetails = cart.findAllCartDTO("baolh");

//		Map<String, CartDetailDTo> cartDetailMap = new HashMap<>();
//
//		for (CartDetailDTo cartDetail : cartDetails) {
//			String uniqueKey = cartDetail.getImg() + "-" + cartDetail.getColor() + "-" + cartDetail.getSize();
//			if (cartDetailMap.containsKey(uniqueKey)) {
//				CartDetailDTo existingCartDetail = cartDetailMap.get(uniqueKey);
//				existingCartDetail.setQuantity((existingCartDetail.getQuantity() + cartDetail.getQuantity()));
//			} else {
//				cartDetailMap.put(uniqueKey, cartDetail);
//			}
//		}
//		for (CartDetailDTo cartDetail : cartDetailMap.values()) {
//			int totalQuantity = cartDetail.getQuantity();
//			if (totalQuantity == 2 ||totalQuantity == 3||totalQuantity == 4 ||totalQuantity == 5  ) {
//				int newQuantity = 1;
//				cartDetail.setQuantity(newQuantity);
//
//			} else {
//				int newQuantity = (int) Math.sqrt(totalQuantity);
//				cartDetail.setQuantity(newQuantity);
//			}
//			// Lấy căn bậc hai của tổng số lượng
//
//		}
//		return new ArrayList<>(cartDetailMap.values());
		return cartDetails;
	}

	@DeleteMapping("/rest/cart/{id}")
	public ResponseEntity<Void> deleteCartDetail(@PathVariable("id") int id) {
		Optional<CartDetail> optionalCartDetail = cart.findById(id);

		if (optionalCartDetail.isPresent()) {
			cart.delete(optionalCartDetail.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/rest/cart")
	public ResponseEntity<Void> deleteCartDetailAll() {
		cart.deleteAll();
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/rest/cart/shop/{idShop}")
	public ResponseEntity<Void> deleteCartDetailsByShopId(@PathVariable("idShop") String idShop) {
		List<CartDetail> listNew = new ArrayList<>();
		List<CartDetail> list = cart.findAll();
		list.forEach(cartDetail -> {
			if (cartDetail.getProductCart().getProduct().getSellerProduct().getUsername().equals(idShop)) {
				listNew.add(cartDetail);
			}
		});
		cart.deleteAllInBatch(listNew);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/rest/cart/{id}")
	public ResponseEntity<CartDetailDTo> updateCartDetail(@PathVariable int id,
			@RequestBody CartDetailDTo updatedCartDetail) {
		CartDetail cartDetail = cart.findById(id).get();

		if (cartDetail != null) {
			cartDetail.setQuantity(updatedCartDetail.getQuantity());

			cart.save(cartDetail);
			return ResponseEntity.ok(updatedCartDetail);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/rest/cart/order")
	public ResponseEntity<Void> order(@RequestBody List<CartDetailDTo> listOrder) {
		Customer user = sessionService.get("user");
		Order order = new Order();
		order.setCreatedDay(new Date());
		System.out.println(new Date());
		order.setNote("Đóng gói kĩ và giao vào giờ hành chính");
		order.setAddress(user.getAddress());
		order.setPhone(user.getPhoneAddress());
		order.setVoucherOrder(null);
		order.setPriceDiscount(0.0);
		order.setCustomerOrder(user);
		order.setOrderStatus(orderStatusService.findById(1001).get());
		order.setPayment(paymentService.findById(1001).get());
		order.setTotal(listOrder.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
		orderService.save(order);

		for (CartDetailDTo item : listOrder) {
			OrderDetails orderDetails = new OrderDetails();
			Order orders = orderService.findLatestOrder();
			orderDetails.setOrder(orders);
			orderDetails.setPrice(item.getPrice());
			CartDetail cartDetail = cart.getById(item.getId());
			int id = cartDetail.getProductCart().getId();
			ProductVariant prod = productVariantService.findById(id).get();
			orderDetails.setProductVariant(prod);
			orderDetails.setQuantity(item.getQuantity());
			orderDetails.setStatus(orderStatusService.findById(1001).get());
			System.out.println(orderDetails.getOrder().getId());
			orderDetailService.save(orderDetails);
			System.out.println("ok");
			cart.deleteById(item.getId());
		}

		return ResponseEntity.ok().build();
	}

//	@PostMapping("/rest/cart")
//	public ResponseEntity<String> createCartDetail(@RequestBody CartDetail cartDetail) {
//	    CartDetail savedCartDetail = cart.save(cartDetail);
//	    return ResponseEntity.ok("Cart detail created successfully with ID: " + savedCartDetail.getId());
//	}
//	
//	@PutMapping("/rest/cart/{id}")
//	public ResponseEntity<String> updateCartDetail(@PathVariable int id, @RequestBody CartDetail updatedCartDetail) {
//	    Optional<CartDetail> optionalCartDetail = cart.findById(id);
//
//	    if (optionalCartDetail.isPresent()) {
//	        CartDetail existingCartDetail = optionalCartDetail.get();
//	        // Cập nhật thông tin của existingCartDetail từ updatedCartDetail
//	        existingCartDetail.setProductCart(updatedCartDetail.getProductCart());
//	        existingCartDetail.setCart(updatedCartDetail.getCart());
//	        existingCartDetail.setQuantity(updatedCartDetail.getQuantity());
//
//	        cart.save(existingCartDetail);
//	        return ResponseEntity.ok("Cart detail updated successfully.");
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}

}
