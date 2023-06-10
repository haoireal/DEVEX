package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.DTO.CartItem;
import com.hnbcoffee.Sevice.ShoppingCartService;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	Map<Integer, CartItem> map = new HashMap<>();
	
	public long priceSize(String size) {
		if(size.equalsIgnoreCase("L")) {
			return 10000;
		}else if(size.equalsIgnoreCase("M")) {
			return 5000;
		}else {
			return 0;
		}
	}
	
	@Override
	public CartItem add(CartItem cartItem) {
		for (Map.Entry<Integer, CartItem> entry : map.entrySet()) {
            Integer key = entry.getKey();
            CartItem value = entry.getValue();
            if(value.getIdBeverage().equals(cartItem.getIdBeverage())) {
            	if(value.getSize().equals(cartItem.getSize())) {
            		value.setQty(value.getQty() + cartItem.getQty());
            		return value;
    			}else {
    				map.put(cartItem.getId(), cartItem);
    				return cartItem;
    			}
            }
        }
		map.put(cartItem.getId(), cartItem);
//		if(item == null) {
//			item = cartItem;
//			map.put(cartItem.getId(), item);
//		} else {
//			if(item.getSize().equals(cartItem.getSize())) {
//				item.setQty(item.getQty() + cartItem.getQty());
//			}else {
////				item = cartItem;
//				map.put(cartItem.getId(), cartItem);
//			}
//		}
		return cartItem;
	}
	
	@Override
	public void remove(Integer id) {
		map.remove(id);
	}
	
	@Override
	public CartItem update(Integer id, int qty, String size) {
		CartItem item = map.get(id);
		item.setQty(qty);
		item.setSize(size);
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
	public CartItem getItemById(Integer id) {
		return map.get(id);
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
				.mapToDouble(item -> (item.getPrice() + priceSize(item.getSize())) *item.getQty())
				.sum();
	}

	
}