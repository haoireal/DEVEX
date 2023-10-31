package com.Devex.Repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Follow;

@EnableJpaRepositories
@Repository("followRepository")
public interface FollowRepository extends JpaRepository<Follow, Integer>{

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.seller.username like ?1")
	int getCountFollowBySellerUsername(String username);
	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.customer.username like ?1")
	int getCountFollowByCustomerUsername(String username);
	
	@Modifying
	@Query(value = "INSERT INTO Follow (Userfollow_ID, Shop_ID, Createdday) VALUES (?1, ?2, ?3)", nativeQuery = true)
	void insertFollow(String userId, String sellerId, Date creadtedday);
}
