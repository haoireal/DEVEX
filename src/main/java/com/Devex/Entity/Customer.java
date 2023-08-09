package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(name = "Address")
	private String address;
	@Column(name = "Phoneaddress")
	private String phoneAddress;
	

	
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<Follow> follow;
	
	@OneToMany(mappedBy = "customerComment")
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "customerVoucherDetails")
	private List<VoucherDetails> voucherDetails;
	
	@OneToMany(mappedBy = "customerOrder")
	private List<Order> orders;
	
	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Cart cart;

}
