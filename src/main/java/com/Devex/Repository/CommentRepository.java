package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Comment;

import java.util.List;

@EnableJpaRepositories
@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query("SELECT DISTINCT cmt FROM Comment cmt " +
            "WHERE cmt.productComment.id = ?1 " +
            "Order BY cmt.createdAt DESC")
    List<Comment> findByProductID(String productId);
}
