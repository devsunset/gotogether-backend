package com.gotogether.repository;

import com.gotogether.entity.Memo;
import com.gotogether.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findBySenderAndSdelete(User user, String sdelete, Pageable pageable);

    Page<Memo> findByReceiverAndRdelete(User user, String rdelete, Pageable pageable);

    long countByReceiverAndReadflagAndRdelete(User user, String readflag, String rdelete);
}
