package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.OrderDetails;

public interface OrderDetailService {

	void deleteAll();

	void delete(OrderDetails entity);

	OrderDetails getById(String id);

	void deleteById(String id);

	long count();

	Optional<OrderDetails> findById(String id);

	List<OrderDetails> findAllById(Iterable<String> ids);

	List<OrderDetails> findAll();

	Page<OrderDetails> findAll(Pageable pageable);

	List<OrderDetails> findAll(Sort sort);

	Optional<OrderDetails> findOne(Example<OrderDetails> example);

	List<OrderDetails> saveAll(List<OrderDetails> entities);

	OrderDetails save(OrderDetails entity);

	List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username);

	void updateIdOrderDetailsStatus(int ido, String id);

	List<OrderDetails> findOrderDetailsByOrderID(String id, String username);

	OrderDetails saveAndFlush(OrderDetails entity);

}
