package com.Devex.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

	String shopName;
	String address;
	String phoneAddress;
	Boolean mall;
	
}
