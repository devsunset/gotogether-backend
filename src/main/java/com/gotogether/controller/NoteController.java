package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.NoteRequest;
import com.gotogether.dto.request.NoteSearchCondition;
import com.gotogether.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody NoteRequest noteRequest) throws Exception {
        return CommonResponse.toResponseEntity(noteService.save(noteRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deletefrom/{noteId}")
    public ResponseEntity<?> deleteFrom(@PathVariable("noteId") Long noteId) throws Exception {
        return CommonResponse.toResponseEntity(noteService.deleteFrom(noteId));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteto/{noteId}")
    public ResponseEntity<?> deleteTo(@PathVariable("noteId") Long noteId) throws Exception {
        return CommonResponse.toResponseEntity(noteService.deleteTo(noteId));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{noteId}")
    public ResponseEntity<?> get(@PathVariable("noteId") Long noteId) throws Exception {
        return CommonResponse.toResponseEntity(noteService.get(noteId));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/list")
    public ResponseEntity<?> getPageList(@PageableDefault(size=10, sort = "noteId", direction = Sort.Direction.DESC)
                                             Pageable pageable,
                                    @Valid @RequestBody NoteSearchCondition noteSearchCondition ) throws Exception {
        return CommonResponse.toResponseEntity(noteService.getList(pageable,noteSearchCondition));
    }
}
