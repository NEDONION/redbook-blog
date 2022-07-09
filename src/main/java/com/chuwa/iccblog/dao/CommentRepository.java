package com.chuwa.iccblog.dao;

import com.chuwa.iccblog.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * TODO: 不用实现。但要学语法
     * @param postId
     * @return
     */
    List<Comment> findByPostId(long postId);
}