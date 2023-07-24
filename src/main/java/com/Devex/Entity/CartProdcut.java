package com.Devex.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProdcut {
	public String id;
	public String name;
	public String img;
	public Double price;
	public String color;
	public String size;
	public int soluong;
	public Double total;
}
