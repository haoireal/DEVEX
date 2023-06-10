package com.hnbcoffee.Entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "Users")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	Integer id;
	@Column(name = "Fullname")
	String fullname;
	@Column(name = "Email")
	String email;
	@Column(name = "Password")
	String password;
	@Column(name = "Gender")
	boolean gender;
	@Column(name = "Birthday")
	private Date birthday;
	@Column(name = "Address")
	String address;
	@Column(name = "Role")
	String role = "CUSTOMER";
	@Column(name = "Verifi_Code")
	int vericode;
	@Column(name = "Active")
	boolean Acive;
}
