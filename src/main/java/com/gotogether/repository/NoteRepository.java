package com.gotogether.repository;

import com.gotogether.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note, Long> {

}
