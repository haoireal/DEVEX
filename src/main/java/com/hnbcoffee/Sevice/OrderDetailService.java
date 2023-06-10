package com.hnbcoffee.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hnbcoffee.DTO.Top6Beverage;
import com.hnbcoffee.Entity.OrderDetail;

public interface OrderDetailService {

	void deleteAll();

	List<OrderDetail> findAll(Example<OrderDetail> example, Sort sort);

	List<OrderDetail> findAll(Example<OrderDetail> example);

	void delete(OrderDetail entity);

	void deleteById(Integer id);

	long count();

	Optional<OrderDetail> findById(Integer id);

	Page<OrderDetail> findAll(Example<OrderDetail> example, Pageable pageable);

	List<OrderDetail> findAllById(List<Integer> ids);

	List<OrderDetail> findAll();

	Page<OrderDetail> findAll(Pageable pageable);

	List<OrderDetail> findAll(Sort sort);

	List<OrderDetail> saveAll(List<OrderDetail> entities);

	OrderDetail save(OrderDetail entity);

	Page<Top6Beverage> findTop6Beverage(Pageable pageable);


}
