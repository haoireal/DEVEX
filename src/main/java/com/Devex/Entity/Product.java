package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@Column(name = "Description")
	private String description;
	@Column(name = "Createdday")
	private Date createdDay;
	@Column(name = "Active")
	private Boolean active;
	@Column(name = "Isdelete")
	private Boolean isdelete;
	
	@Formula("(SELECT COUNT(od.ID) FROM Order_Details od INNER JOIN Product_Variant pv ON od.Product_ID = pv.ID WHERE pv.Product_ID = ID)")
    private Integer soldCount;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Shop_ID")
	private Seller sellerProduct;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Category_ID")
	private CategoryDetails categoryDetails;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Brand_ID")
	private ProductBrand productbrand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ImageProduct> imageProducts;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<ProductVariant> productVariants;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productComment")
	private List<Comment> comments;

}
