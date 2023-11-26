package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Dwallet;

import java.util.List;

@EnableJpaRepositories
@Repository("dwalletRepository")
public interface DwalletRepository extends JpaRepository<Dwallet, String>{
    @Query("SELECT dw.id FROM Dwallet dw WHERE dw.user.username like ?1")
    String findDwalletIDbyUsername(String username);
}
