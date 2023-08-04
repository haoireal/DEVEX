package com.Devex.Controller;

import java.util.List;
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
import com.Devex.Repository.CartDetailRespository;
import com.Devex.Sevice.CartDetailDTo;

@CrossOrigin("*")
@RestController
public class CartAPIController {
	@Autowired
	CartDetailRespository cart;
	
	@GetMapping("/rest/cart")
	public List<CartDetailDTo> getAll(Model model){
		return cart.findAllCartDTO();
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
