package com.hnbcoffee.Sevice;

import java.util.Collection;

import com.hnbcoffee.DTO.CartItem;


public interface ShoppingCartService {

	CartItem add(CartItem itemCart);

	void remove(Integer id);

	CartItem update(Integer id, int qty, String size);

	void clear();

	Collection<CartItem> getItems();
	
	CartItem getItemById(Integer id);

	int getCount();

	double getAmount();
}