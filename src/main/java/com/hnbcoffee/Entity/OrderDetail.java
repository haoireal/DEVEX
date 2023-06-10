package com.hnbcoffee.Entity;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders_Detail")
public class OrderDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
    @Column(name = "Size")
    private String size;
    
    @Column(name = "Price")
    private Double price;
    
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Orders_ID")
    private Order order;
    
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Beverage_ID")
    Beverage beverage;
    
}




