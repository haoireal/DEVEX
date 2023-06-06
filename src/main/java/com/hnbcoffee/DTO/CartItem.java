package com.hnbcoffee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	Integer id;
	String name;
	Double price;
	String size;
	String image;
	int qty;
}
