package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FlashSale_Time")
public class FlashSale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	@Column(name = "Discount")
	private int discount;
	@Column(name = "Amountsell")
	private int amountSell;
	@Column(name = "Amountorder")
	private int amountOrder;
	@Column(name = "Status")
	private Boolean status;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Time")
	private FlashSaleTime flashSaleTime;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private ProductVariant productVariant;

}
