package com.Devex.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Sellers")
public class Seller implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Shop_ID")
	private String shopId;
	@Column(name = "Address")
	private String address;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "Mall")
	private Boolean mall;
	@Column(name = "Active")
	private Boolean active;
	
	@OneToOne
	@JoinColumn(name = "User_ID")
	private User user;
	
	@OneToMany(mappedBy = "seller")
	private List<Follow> follows;
	
	@OneToMany(mappedBy = "sellerProduct")
	private List<Product> products;

}
