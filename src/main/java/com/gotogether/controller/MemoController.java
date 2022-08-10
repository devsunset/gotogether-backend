package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.MemoRequest;
import com.gotogether.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/memo")
@RequiredArgsConstructor
public class MemoController {
    @Autowired
    private final MemoService memoService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody MemoRequest memoRequest) throws Exception {
        return CommonResponse.toResponseEntity(memoService.save(memoRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deletefrom/{memoId}")
    public ResponseEntity<?> deleteFrom(@PathVariable("noteId") Long memoId) throws Exception {
        return CommonResponse.toResponseEntity(memoService.deleteFrom(memoId));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteto/{memoId}")
    public ResponseEntity<?> deleteTo(@PathVariable("noteId") Long memoId) throws Exception {
        return CommonResponse.toResponseEntity(memoService.deleteTo(memoId));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{memoId}")
    public ResponseEntity<?> get(@PathVariable("memoId") Long noteId) throws Exception {
        return CommonResponse.toResponseEntity(memoService.get(noteId));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/newreceivememo")
    public ResponseEntity<?> getNewNote() throws Exception {
        return CommonResponse.toResponseEntity(memoService.getNewReceiveNote());
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/sendlist")
    public ResponseEntity<?> getPageSendList(@PageableDefault(size = 10, sort = "noteId", direction = Sort.Direction.DESC)
                                             Pageable pageable) throws Exception {
        return CommonResponse.toResponseEntity(memoService.getSendList(pageable));
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/receivelist")
    public ResponseEntity<?> getPageReceiveList(@PageableDefault(size = 10, sort = "noteId", direction = Sort.Direction.DESC)
                                                Pageable pageable) throws Exception {
        return CommonResponse.toResponseEntity(memoService.getReceiveList(pageable));
    }
}
