package com.gotogether.repository;

import com.gotogether.entity.Note;
import com.gotogether.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByFromUserAndFromDeleted(User fromUser, String deleted, Pageable pageable);

    Page<Note> findByToUserAndToDeleted(User toUser, String deleted, Pageable pageable);

    long countByToUserAndReadAndToDeleted(User user, String read, String toDeleted);
}
