package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Vouchers")
public class Voucher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private int id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Discount")
	private Double discount;
	@Column(name = "Note")
	private String note;
	@Column(name = "Startdate")
	private Date startDate;
	@Column(name = "Enddate")
	private Date endDate;
	@Column(name = "Code")
	private String code;
	@Column(name = "Banner")
	private String banner;
	
	@OneToMany(mappedBy = "voucher")
	private List<VoucherDetails> voucherDetails;
	
	@OneToMany(mappedBy = "voucherOrder")
	private List<Order> orders;
	

}
