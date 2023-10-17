package com.Devex.DTO;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashSaleDTO {
	String discount;
	String amountSell;
	String amountOrder;
	Boolean status;
	int fashSaleTimeId;
	int productVariantId;
	
}
