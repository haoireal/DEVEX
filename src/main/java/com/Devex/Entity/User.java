package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Username", updatable = false)
	private String username;
	@Column(name = "Fullname")
	private String fullname;
	@Column(name = "Email")
	private String email;
	@Column(name = "Password")
	private String password;
	@Column(name = "Avatar")
	private String avatar;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "Createdday")
	private Date createDay;
	@Column(name = "Active")
	private Boolean active;
	
	@ManyToOne
	@JoinColumn(name = "Role_ID")
	private Role role;
	
	@OneToOne(mappedBy = "user")
	private Customer customer;
	
	@OneToOne(mappedBy = "user")
	private Seller seller;
	
	
}
