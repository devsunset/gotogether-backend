package com.gotogether.repository;

import com.gotogether.entity.Memo;
import com.gotogether.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByFromUserAndFromDeleted(User fromUser, String deleted, Pageable pageable);

    Page<Memo> findByToUserAndToDeleted(User toUser, String deleted, Pageable pageable);

    long countByToUserAndReadAndToDeleted(User user, String read, String toDeleted);
}
