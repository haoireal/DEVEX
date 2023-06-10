package com.hnbcoffee.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.DTO.Top6Beverage;
import com.hnbcoffee.Entity.OrderDetail;


@EnableJpaRepositories
@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	@Query("SELECT new Top6Beverage(o.beverage, sum(o.quantity)) FROM OrderDetail o GROUP BY o.beverage ORDER BY sum(o.quantity) DESC")
	Page<Top6Beverage> findTop6Beverage(Pageable pageable);

	
}
