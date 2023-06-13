package com.hnbcoffee.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hnbcoffee.Entity.Order;
import com.hnbcoffee.Entity.User;

public interface OrderService {

	void deleteAll();

	List<Order> findAll(Example<Order> example, Sort sort);

	List<Order> findAll(Example<Order> example);

	void delete(Order entity);

	void deleteById(Integer id);

	long count();

	Optional<Order> findById(Integer id);

	Page<Order> findAll(Example<Order> example, Pageable pageable);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	List<Order> findAll(Sort sort);

	List<Order> saveAll(List<Order>entities);

	Order save(Order entity);

	List<Order> findByAccount(User user);

	double getTotalMoney();

}
