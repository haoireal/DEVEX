package com.hnbcoffee.Utils;

import java.util.HashMap;

import com.hnbcoffee.DTO.CartItem;
import com.hnbcoffee.Entity.*;

public class DataSharing {

	public static HashMap<Integer, CartItem> items = new HashMap<>();

	static {
		// Add product into products list
		items.put(1, new CartItem(1, "Cà phê sữa đá", 25000.0, "M", "ca-phe-sua-da.png", 0));
		items.put(2, new CartItem(2, "Cà phê đen đá", 25000.0, "M", "ca-phe-den-da.png", 0));
		items.put(3, new CartItem(3, "Bạc xỉu", 39000.0, "M", "bac-siu.png", 0));
		items.put(4, new CartItem(4, "Americano Đá", 35000.0, "M",  "americano-da.png", 0));
		items.put(5, new CartItem(5, "Caramel Macchiato đá", 40000.0, "M", "caramel-macchiato.png", 0));
				
	}

}
