package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.UserSearch;

@EnableJpaRepositories
@Repository("userSearchRepository")
public interface UserSearchRespository extends JpaRepository<UserSearch, Integer>{

}
