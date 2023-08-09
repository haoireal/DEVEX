package com.Devex.Controller;

import java.util.ArrayList;
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

import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Customer;
import com.Devex.Entity.User;
import com.Devex.Repository.CartDetailRespository;
import com.Devex.Sevice.CartDetailDTo;
import com.Devex.Sevice.SessionService;

@CrossOrigin("*")
@RestController
public class CartAPIController {
	@Autowired
	CartDetailRespository cart;
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/rest/cart")
	public List<CartDetailDTo> getAll(Model model){
		List<CartDetailDTo> cartDetails = cart.findAllCartDTO("qbfegl329");

	    Map<String, CartDetailDTo> cartDetailMap = new HashMap<>();

	    for (CartDetailDTo cartDetail : cartDetails) {
	        String uniqueKey = cartDetail.getImg()+ "-" + cartDetail.getColor() + "-" + cartDetail.getSize();
	        if (cartDetailMap.containsKey(uniqueKey)) {
	            CartDetailDTo existingCartDetail = cartDetailMap.get(uniqueKey);
	            existingCartDetail.setQuantity((existingCartDetail.getQuantity() + cartDetail.getQuantity()));
	        } else {
	            cartDetailMap.put(uniqueKey, cartDetail);
	        }
	    }
	    for (CartDetailDTo cartDetail : cartDetailMap.values()) {
	        int totalQuantity = cartDetail.getQuantity();
	        if(totalQuantity==2 || totalQuantity == 3 || totalQuantity==4) {
	        	int newQuantity = 1;
	        	cartDetail.setQuantity(newQuantity);
	        	
	        }else {
	        	int newQuantity = (int) Math.sqrt(totalQuantity);
	        	cartDetail.setQuantity(newQuantity);
	        }
	         // Lấy căn bậc hai của tổng số lượng
	        
	    }
	    return new ArrayList<>(cartDetailMap.values());
		}
	
	@DeleteMapping("/rest/cart/{id}")
	public ResponseEntity<String> deleteCartDetail(@PathVariable int id) {
	    Optional<CartDetail> optionalCartDetail = cart.findById(id);

	    if (optionalCartDetail.isPresent()) {
	        cart.delete(optionalCartDetail.get());
	        return ResponseEntity.ok("Cart detail deleted successfully.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	@PostMapping("/rest/cart")
	public ResponseEntity<String> createCartDetail(@RequestBody CartDetail cartDetail) {
	    CartDetail savedCartDetail = cart.save(cartDetail);
	    return ResponseEntity.ok("Cart detail created successfully with ID: " + savedCartDetail.getId());
	}
	@PutMapping("/rest/cart/{id}")
	public ResponseEntity<String> updateCartDetail(@PathVariable int id, @RequestBody CartDetail updatedCartDetail) {
	    Optional<CartDetail> optionalCartDetail = cart.findById(id);

	    if (optionalCartDetail.isPresent()) {
	        CartDetail existingCartDetail = optionalCartDetail.get();
	        // Cập nhật thông tin của existingCartDetail từ updatedCartDetail
	        existingCartDetail.setProductCart(updatedCartDetail.getProductCart());
	        existingCartDetail.setCart(updatedCartDetail.getCart());
	        existingCartDetail.setQuantity(updatedCartDetail.getQuantity());

	        cart.save(existingCartDetail);
	        return ResponseEntity.ok("Cart detail updated successfully.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}



}
