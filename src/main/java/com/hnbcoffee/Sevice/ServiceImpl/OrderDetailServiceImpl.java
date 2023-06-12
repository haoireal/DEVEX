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

import com.hnbcoffee.DTO.ReportSale;
import com.hnbcoffee.DTO.TopBeverage;
import com.hnbcoffee.Entity.OrderDetail;
import com.hnbcoffee.Repository.OrderDetailRepository;
import com.hnbcoffee.Sevice.OrderDetailService;

@SessionScope
@Service("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailRepository orderDetail;
	
	@Override
	public Page<TopBeverage> findTopBeverage(Pageable pageable) {
		return orderDetail.findTopBeverage(pageable);
	}
	
	

	@Override
	public List<TopBeverage> findTopBeverage() {
		return orderDetail.findTopBeverage();
	}



	@Override
	public OrderDetail save(OrderDetail entity) {
		return orderDetail.save(entity);
	}

	@Override
	public List<OrderDetail> saveAll(List<OrderDetail> entities) {
		return orderDetail.saveAll(entities);
	}

	@Override
	public List<OrderDetail> findAll(Sort sort) {
		return orderDetail.findAll(sort);
	}

	@Override
	public Page<OrderDetail> findAll(Pageable pageable) {
		return orderDetail.findAll(pageable);
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetail.findAll();
	}

	@Override
	public List<OrderDetail> findAllById(List<Integer> ids) {
		return orderDetail.findAllById(ids);
	}

	@Override
	public Page<OrderDetail> findAll(Example<OrderDetail> example, Pageable pageable) {
		return orderDetail.findAll(example, pageable);
	}

	@Override
	public Optional<OrderDetail> findById(Integer id) {
		return orderDetail.findById(id);
	}

	@Override
	public long count() {
		return orderDetail.count();
	}

	@Override
	public void deleteById(Integer id) {
		orderDetail.deleteById(id);
	}

	@Override
	public void delete(OrderDetail entity) {
		orderDetail.delete(entity);
	}

	@Override
	public List<OrderDetail> findAll(Example<OrderDetail> example) {
		return orderDetail.findAll(example);
	}

	@Override
	public List<OrderDetail> findAll(Example<OrderDetail> example, Sort sort) {
		return orderDetail.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		orderDetail.deleteAll();
	}

	@Override
	public List<ReportSale> reportSale() {
		return orderDetail.reportSale();
	}

//	@Override
//	public List<ReportSale> reportSale(Sort sort) {
//		return orderDetail.reportSale(sort);
//	}

//	@Override
//	public Page<ReportSale> reportSale(Pageable pageable) {
//		return orderDetail.reportSale(pageable);
//	}
	
	
	
	
}
