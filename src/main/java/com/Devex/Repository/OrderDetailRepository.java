package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.OrderDetails;

@EnableJpaRepositories
@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetails, String>{

	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE od.id = ?1 AND s.username = ?2")
	List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username);
	
}
