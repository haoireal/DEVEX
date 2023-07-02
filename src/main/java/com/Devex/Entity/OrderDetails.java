package com.Devex.Entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Order_Details")
public class OrderDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", columnDefinition = "varchar(50) DEFAULT CONVERT(VARCHAR(50), CRYPT_GEN_RANDOM(30), 2)", nullable = false)
	private String id;
	@Column(name = "Quantity")
	private int quantity;
	@Column(name = "Price")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "Order_ID")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private ProductVariant productVariant;
	
	// Constructor
    public OrderDetails() {
        this.id = generateRandomId();
    }

    // Helper method to generate random ID
    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }

}
