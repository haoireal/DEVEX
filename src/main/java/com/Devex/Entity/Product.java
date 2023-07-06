package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", updatable = false)
	private String id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Brand")
	private String brand;
	@Column(name = "Description")
	private String description;
	@Column(name = "Createdday")
	private Date createdDay;
	@Column(name = "Active")
	private Boolean active;
	
	@ManyToOne
	@JoinColumn(name = "Shop_ID")
	private Seller sellerProduct;
	
	@ManyToOne
	@JoinColumn(name = "Category_ID")
	private CategoryDetails categoryDetails;
	
	@OneToMany(mappedBy = "product")
	private List<ImageProduct> imageProducts;
	
	@OneToMany(mappedBy = "productVariant")
	private List<ProductVariant> productVariants;
	
	@OneToMany(mappedBy = "productComment")
	private List<Comment> comments;

}
