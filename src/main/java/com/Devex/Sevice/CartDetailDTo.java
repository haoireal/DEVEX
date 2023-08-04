package com.Devex.Sevice;

import java.util.List;

import com.Devex.Entity.ImageProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDTo {
	private int id;
	private Double price;
	private int idcart;
    private int quantity;
    private String nameProduct;
    private String color;
    private String size;
    private String nameShop;
//    private String img;
    
}
