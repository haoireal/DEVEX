package com.Devex.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String idShop;
    private String idProduct;
    private String avatarShop;
    private String img;
    private Date createdDay;
   
    
}
