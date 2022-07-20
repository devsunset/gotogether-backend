package com.gotogether.repository;

import com.gotogether.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying
    @Query("update Post p set p.hit = p.hit + 1 where p.post_id = :post_id")
    int updateHit(Long post_id);
}
