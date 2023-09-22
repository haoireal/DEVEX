package com.Devex.Sevice.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.Entity.OrderDetails;
import com.Devex.Repository.OrderDetailRepository;
import com.Devex.Sevice.OrderDetailService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("orderDetailsService")
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetails save(OrderDetails entity) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = dateFormat.format(new Date());

		// Kết hợp thời gian với một số ngẫu nhiên để tạo chuỗi duy nhất
		int randomSuffix = (int) (Math.random() * 1000000);
		String timeBasedString = currentTime + "-" + randomSuffix;
		entity.setId(timeBasedString);
		System.out.println(timeBasedString);
		return orderDetailRepository.save(entity);
	}

	@Override
	public OrderDetails saveAndFlush(OrderDetails entity) {
		
		return orderDetailRepository.saveAndFlush(entity);
	}

	@Override
	public List<OrderDetails> saveAll(List<OrderDetails> entities) {
		return orderDetailRepository.saveAll(entities);
	}

	@Override
	public Optional<OrderDetails> findOne(Example<OrderDetails> example) {
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

	@Override
	public List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username) {
		return orderDetailRepository.findOrderDetailsByOrderIDAndSellerUsername(id, username);
	}

	@Override
	@Transactional
	public void updateIdOrderDetailsStatus(int ido, String id) {
		orderDetailRepository.updateIdOrderDetailsStatus(ido, id);
	}

	@Override
	public List<OrderDetails> findOrderDetailsByOrderID(String id, String username) {
		return orderDetailRepository.findOrderDetailsByOrderID(id, username);
	}

	@Override
	public List<Object[]> getTotalPriceByMonthAndSellerUsername(int year, int month, String username) {
		return orderDetailRepository.getTotalPriceByMonthAndSellerUsername(year, month, username);
	}

	@Override
	public List<Object[]> getTotalPriceByYearAndSellerUsername(int year, String username) {
		return orderDetailRepository.getTotalPriceByYearAndSellerUsername(year, username);
	}

	@Override
	public int getTotalOrderDetailsByStatusIdAndSellerUsername(int statusid, String username) {
		return orderDetailRepository.getTotalOrderDetailsByStatusIdAndSellerUsername(statusid, username);
	}
	
	

}
