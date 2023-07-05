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

import com.Devex.Entity.OrderDetails;
import com.Devex.Repository.OrderDetailRepository;
import com.Devex.Sevice.OrderDetailService;

@SessionScope
@Service("orderDetailsService")
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetails save(OrderDetails entity) {
		entity.setId("1");
		return orderDetailRepository.save(entity);
	}

	@Override
	public List<OrderDetails> saveAll(List<OrderDetails> entities) {
		return orderDetailRepository.saveAll(entities);
	}

	@Override
	public  Optional<OrderDetails> findOne(Example<OrderDetails> example) {
		return orderDetailRepository.findOne(example);
	}

	@Override
	public List<OrderDetails> findAll(Sort sort) {
		return orderDetailRepository.findAll(sort);
	}

	@Override
	public Page<OrderDetails> findAll(Pageable pageable) {
		return orderDetailRepository.findAll(pageable);
	}

	@Override
	public List<OrderDetails> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetails> findAllById(Iterable<String> ids) {
		return orderDetailRepository.findAllById(ids);
	}

	@Override
	public Optional<OrderDetails> findById(String id) {
		return orderDetailRepository.findById(id);
	}

	@Override
	public long count() {
		return orderDetailRepository.count();
	}

	@Override
	public void deleteById(String id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	public OrderDetails getById(String id) {
		return orderDetailRepository.getById(id);
	}

	@Override
	public void delete(OrderDetails entity) {
		orderDetailRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		orderDetailRepository.deleteAll();
	}
	
	
	
	
}
