package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.OrderStatus;

@EnableJpaRepositories
@Repository("orderStatusRepository")
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{

}
