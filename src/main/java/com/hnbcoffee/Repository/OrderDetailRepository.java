package com.hnbcoffee.Repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.DTO.ReportSale;
import com.hnbcoffee.DTO.TopBeverage;
import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Entity.OrderDetail;


@EnableJpaRepositories
@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	@Query("SELECT new TopBeverage(o.beverage, sum(o.quantity)) FROM OrderDetail o GROUP BY o.beverage ORDER BY sum(o.quantity) DESC")
	Page<TopBeverage> findTopBeverage(Pageable pageable);
	
	@Query("SELECT new TopBeverage(o.beverage, sum(o.quantity)) FROM OrderDetail o GROUP BY o.beverage ORDER BY sum(o.quantity) DESC")
	List<TopBeverage> findTopBeverage();

//	@Query("SELECT new ReportSale(o.beverage.id, o.beverage.name, o.beverage.image, sum(o.quantity), sum(o.price)) FROM OrderDetail o GROUP BY o.beverage.id, o.beverage.name")
//	Page<ReportSale> reportSale(Pageable pageable);
	
//	@Query("SELECT new ReportSale(o.beverage.id, o.beverage.name, o.beverage.image, sum(o.quantity), sum(o.price)) FROM OrderDetail o GROUP BY o.beverage.id, o.beverage.name, o.beverage.image")
//	List<ReportSale> reportSale(Sort sort);
	
//	@Query("SELECT new ReportSale(b.id, b.name, b.image, SUM(od.quantity), SUM(od.price)) FROM Beverage b JOIN b.orderDetail od GROUP BY b.id, b.name, b.image ORDER BY SUM(od.quantity) DESC")
//	List<ReportSale> reportSale(Sort sort);
	
	@Query("SELECT new ReportSale(o.beverage, sum(o.quantity), sum(o.price)) FROM OrderDetail o GROUP BY o.beverage ORDER BY sum(o.quantity) DESC")
	List<ReportSale> reportSale();
}
