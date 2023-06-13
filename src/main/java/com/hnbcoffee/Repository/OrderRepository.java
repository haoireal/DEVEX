package com.hnbcoffee.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.DTO.ReportSale;
import com.hnbcoffee.Entity.Order;
import com.hnbcoffee.Entity.User;

@EnableJpaRepositories
@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Query("SELECT o FROM Order o WHERE o.customer = :user AND o.status = true")
	List<Order> findByAccount(User user);
	
	@Query("SELECT sum(o.total) FROM Order o")
	double getTotalMoney();
}
