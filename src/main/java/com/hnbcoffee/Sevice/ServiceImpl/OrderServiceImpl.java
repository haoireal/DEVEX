package com.hnbcoffee.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.hnbcoffee.Entity.Order;
import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Repository.OrderRepository;
import com.hnbcoffee.Sevice.OrderService;

@SessionScope
@Service("orderService")
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderRepository order;

	@Override
	public Order save(Order entity) {
		return order.save(entity);
	}

	@Override
	public List<Order> saveAll(List<Order>entities) {
		return order.saveAll(entities);
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return order.findAll(sort);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return order.findAll(pageable);
	}

	@Override
	public List<Order> findAll() {
		return order.findAll();
	}

	@Override
	public Page<Order> findAll(Example<Order> example, Pageable pageable) {
		return order.findAll(example, pageable);
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return order.findById(id);
	}

	@Override
	public double getTotalMoney() {
		return order.getTotalMoney();
	}

	@Override
	public long count() {
		return order.count();
	}

	@Override
	public void deleteById(Integer id) {
		order.deleteById(id);
	}

	@Override
	public void delete(Order entity) {
		order.delete(entity);
	}

	@Override
	public List<Order> findAll(Example<Order> example) {
		return order.findAll(example);
	}

	@Override
	public List<Order> findAll(Example<Order> example, Sort sort) {
		return order.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		order.deleteAll();
	}

	@Override
	public List<Order> findByAccount(User user) {
		return order.findByAccount(user);
	}
	
	
	
}
