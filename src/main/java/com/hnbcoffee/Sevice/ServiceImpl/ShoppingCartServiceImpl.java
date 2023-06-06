package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.DTO.CartItem;
import com.hnbcoffee.Sevice.ShoppingCartService;
import com.hnbcoffee.Utils.DataSharing;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	Map<Integer, CartItem> map = new HashMap<>();
	@Override
	public CartItem add(CartItem cartItem) {
		CartItem item = map.get(cartItem.getId());
		if(item == null) {
			item = cartItem;
			map.put(cartItem.getId(), item);
		} else {
			item.setQty(item.getQty() + cartItem.getQty());
		}
		return item;
	}
	
	@Override
	public void remove(Integer id) {
		map.remove(id);
	}
	
	@Override
	public CartItem update(Integer id, int qty) {
		CartItem item = map.get(id);
		item.setQty(qty);
		return item;
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Collection<CartItem> getItems() {
		return map.values();
	}
	
	@Override
	public int getCount() {

		return map.values().stream()
				.mapToInt(item -> item.getQty())
				.sum();
	}
	
	@Override
	public double getAmount() {
		return map.values().stream()
				.mapToDouble(item -> item.getPrice()*item.getQty())
				.sum();
	}
}