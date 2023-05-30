package com.hnbcoffee.Entity;

import java.sql.Date;

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

@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	Integer id;
	@Column(name = "UserName")
	String username;
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
	String role;
	@Column(name = "VerifiCode")
	int vericode;
	@Column(name = "Active")
	boolean isveri;
}
