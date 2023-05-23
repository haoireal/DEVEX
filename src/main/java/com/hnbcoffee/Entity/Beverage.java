package com.hnbcoffee.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beverage {
	int id;
	String name;
	Double price;
	String descriptiton;
	String image;
	int typeID;
}
