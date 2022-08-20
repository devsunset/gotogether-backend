package com.gotogether.repository;

import com.gotogether.entity.Together;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TogetherRepository extends JpaRepository<Together, Long> {
    @Transactional
    @Modifying
    @Query("update Together t set t.hit = t.hit + 1 where t.togetherId = :togetherId")
    int updateHit(Long togetherId);

    List<Together> findTop3ByTogetherIdGreaterThanOrderByTogetherIdDesc(Long togetherId);

    Page<Together> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrCategoryContainsIgnoreCaseOrInvolveTypeContainsIgnoreCaseOrSkillContainsIgnoreCase(String title, String content, String category, String involveType, String skill, Pageable pageable);

}



