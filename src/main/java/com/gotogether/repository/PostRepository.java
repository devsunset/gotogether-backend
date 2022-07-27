package com.gotogether.repository;

import com.gotogether.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("update Post p set p.hit = p.hit + 1 where p.postId = :postId")
    int updateHit(Long postId);

    Page<Post> findByCategory(String category, Pageable pageable);

    Page<Post> findByCategoryAndTitleContainsIgnoreCaseOrCategoryAndContentContainsIgnoreCase(String categoryOne, String title,String categoryTwo, String content,Pageable pageable);

}
