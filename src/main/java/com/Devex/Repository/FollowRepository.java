package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Follow;

@EnableJpaRepositories
@Repository("followRepository")
public interface FollowRepository extends JpaRepository<Follow, Integer>{

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.seller.username like ?1")
	int getCountFollowBySellerUsername(String username);
	
}
