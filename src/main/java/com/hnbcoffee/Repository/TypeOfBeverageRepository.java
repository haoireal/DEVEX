package com.hnbcoffee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hnbcoffee.Entity.TypeOfBeverage;

@EnableJpaRepositories
@Repository("typeOfBeverageRepository")
public interface TypeOfBeverageRepository extends JpaRepository<TypeOfBeverage, Integer> {
	
	TypeOfBeverage findByNameLike(String name);
}
