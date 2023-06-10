package com.hnbcoffee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.Order;

@EnableJpaRepositories
@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
