package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.DTO.StatisticalRevenueMonthDTO;
import com.Devex.Entity.OrderDetails;

@EnableJpaRepositories
@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetails, String>{

	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE od.id = ?1 AND s.username = ?2")
	List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username);
	
	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE od.id like ?1 AND sellerProduct.username like ?2")
	List<OrderDetails> findOrderDetailsByOrderID(String id, String username);
	
	@Modifying
	@Query(value = "UPDATE Order_Details SET Status_ID = :statusId WHERE ID = :id", nativeQuery = true)
	void updateIdOrderDetailsStatus(@Param("statusId") int statusId, @Param("id") String id);
	
	@Query("SELECT FUNCTION('DAY', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('DAY', o.createdDay)")
	List<Object[]> getTotalPriceByMonthAndSellerUsername(@Param("year") int year,
		                                                     @Param("month") int month,
		                                                     @Param("username") String username);
	
	@Query("SELECT FUNCTION('MONTH', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('MONTH', o.createdDay)")
	List<Object[]> getTotalPriceByYearAndSellerUsername(@Param("year") int year, @Param("username") String username);
	
	@Query("SELECT SUM(od) FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND od.status.id = :statusid")
	int getTotalOrderDetailsByStatusIdAndSellerUsername(@Param("statusid") int statusid, @Param("username") String username);
	
	@Query("SELECT cd.Name, COUNT(od.productVariant.id) AS productCount " +
		       "FROM OrderDetails od " +
		       "JOIN od.order o " +
		       "JOIN od.productVariant pv " +
		       "JOIN pv.product p " +
		       "JOIN p.categoryDetails cd " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY cd.Name " +
		       "ORDER BY productCount DESC " +
		       "LIMIT 5")
	List<Object[]> getTop5CategoryDetailsAndAmountProductSell(@Param("year") int year);

	
}