package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Seller;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, String>{

	Seller findFirstByUsername(String username);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Sellers (Username, Shopname, Address, Phoneaddress, Mall, Activeshop, Description) VALUES (?,?,?,?,?,?,?)", nativeQuery = true)
	void insertSeller(String Username,String Shopname,String Address,String Phoneaddress,Boolean Mall, Boolean Activeshop, String Description);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE Sellers SET Shopname = ?, Address = ?, Phoneaddress = ?, Mall = ?, Activeshop = ?, Description = ? WHERE Username = ?", nativeQuery = true)
	void updateSeller(String Shopname, String Address, String Phoneaddress, Boolean Mall, Boolean Activeshop, String Description, String Username);
}
