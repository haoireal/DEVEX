package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CommentReply;

@EnableJpaRepositories
@Repository("commentReplyRepository")
public interface CommentReplyRepository extends JpaRepository<CommentReply, Integer>{

}
