package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Order;


public interface OrderRepository extends JpaRepository<Order, String>{

	@Query("SELECT DISTINCT o FROM Order o " +
	           "JOIN FETCH o.orderDetails od " +
	           "JOIN FETCH od.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE s.username = ?1")
	    List<Order> findOrdersBySellerUsername(String sellerUsername);
	
}
