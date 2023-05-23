package com.hnbcoffee.Utils;

import java.util.HashMap;

import com.hnbcoffee.Entity.*;

public class DataSharing {

	public static HashMap<Integer, Beverage> beverages = new HashMap<>();

	static {
		// Add product into products list
		beverages.put(1,
				new Beverage(1, "Cà phê sữa đá", 25000.0, "ĐÂY LÀ CÀ PHÊ SỮA ĐÁ", "ca-phe-sua-da.png", 101));
		beverages.put(2,
				new Beverage(2, "Cà phê đen đá", 25000.0, "ĐÂY LÀ CÀ PHÊ ĐEN ĐÁ", "ca-phe-den-da.png", 101));
		beverages.put(3, new Beverage(3, "Bạc xỉu", 39000.0, "ĐÂY LÀ BẠC XỈU", "bac-siu.png", 101));
		beverages.put(4, new Beverage(4, "Americano Đá", 35000.0, "ĐÂY LÀ AMERICANO ĐÁ", "americano-da.png ", 101));
		beverages.put(5, new Beverage(5, "Caramel Macchiato đá", 40000.0, "ĐÂY LÀ CARAMEL MACCHIATO ĐÁ",
				"caramel-macchiato.png", 101));
	}

}
