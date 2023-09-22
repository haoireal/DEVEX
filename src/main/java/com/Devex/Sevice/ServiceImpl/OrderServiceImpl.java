package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Order;
import com.Devex.Repository.OrderRepository;
import com.Devex.Sevice.OrderService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("orderService")
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order save(Order entity) {
		entity.setId("1");
		return orderRepository.save(entity);
	}
	
	@Override
	public Order saveAndFlush(Order entity) {
		entity.setId("1");
		return orderRepository.saveAndFlush(entity);
	}

	@Override
	public Order findLatestOrder() {
		return orderRepository.findLatestOrder();
	}



	@Override
	public List<Order> saveAll(List<Order> entities) {
		return orderRepository.saveAll(entities);
	}

	@Override
	public Optional<Order> findOne(Example<Order> example) {
		return orderRepository.findOne(example);
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return orderRepository.findAll(sort);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findAllById(Iterable<String> ids) {
		return orderRepository.findAllById(ids);
	}

	@Override
	public Optional<Order> findById(String id) {
		return orderRepository.findById(id);
	}

	@Override
	public long count() {
		return orderRepository.count();
	}

	@Override
	public void deleteById(String id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Order getById(String id) {
		return orderRepository.getById(id);
	}

	@Override
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		orderRepository.deleteAll();
	}

	@Override
	public List<Order> findOrdersBySellerUsername(String sellerUsername) {
		return orderRepository.findOrdersBySellerUsername(sellerUsername);
	}

	@Override
	public Order findOrderById(String id) {
		return orderRepository.findOrderById(id);
	}

	@Override
	@Transactional
	public void updateIdOrderStatus(int ido, String id) {
		orderRepository.updateIdOrderStatus(ido, id);
	}

	@Override
	public List<Order> findOrdersByCustomerID(String customerID) {
		
		return orderRepository.findOrdersByCustomerID(customerID);
	}
	
	@Override
	public Double getTotalOrderValueForSeller(String username) {
		return orderRepository.getTotalOrderValueForSeller(username);
	}

	@Override
	public int getCountOrderForSeller(String username) {
		return orderRepository.getCountOrderForSeller(username);
	}

	@Override
	public int getTotalOrderByStatusIdAndSellerUsername(int statusid, int statusid1, String username) {
		return orderRepository.getTotalOrderByStatusIdAndSellerUsername(statusid, statusid1, username);
	}

	@Override
	public int getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(int statusid, String username) {
		return orderRepository.getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(statusid, username);
	}
	
	

}
