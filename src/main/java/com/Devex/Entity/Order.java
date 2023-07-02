package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", columnDefinition = "varchar(50) DEFAULT CONVERT(VARCHAR(50), CRYPT_GEN_RANDOM(25), 2)", nullable = false)
	private String id;
	@Column(name = "Note")
	private String note;
	@Column(name = "CreatedDay")
	private Date createdDay;
	@Column(name = "Total")
	private Double total;
	@Column(name = "PriceDiscount")
	private Double priceDiscount;
	
	@ManyToOne
	@JoinColumn(name = "Payment_ID")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "Status_ID")
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "Customer_ID")
	private Customer customerOrder;
	
	@ManyToOne
	@JoinColumn(name = "Voucher_ID")
	private Voucher voucherOrder;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails;
	
	// Constructor
    public Order() {
        this.id = generateRandomId();
    }

    // Helper method to generate random ID
    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }

}
