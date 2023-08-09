package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Order;

public interface OrderService {

	void deleteAll();

	void delete(Order entity);

	Order getById(String id);

	void deleteById(String id);

	long count();

	Optional<Order> findById(String id);

	List<Order> findAllById(Iterable<String> ids);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	List<Order> findAll(Sort sort);

	Optional<Order> findOne(Example<Order> example);

	List<Order> saveAll(List<Order> entities);

	Order save(Order entity);

	List<Order> findOrdersBySellerUsername(String sellerUsername);

	Order findOrderById(String id);

	void updateIdOrderStatus(int ido, String id);

	Order saveAndFlush(Order entity);

	Order findLatestOrder();

}
