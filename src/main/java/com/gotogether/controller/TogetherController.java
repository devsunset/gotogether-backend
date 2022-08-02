package com.gotogether.controller;

import com.gotogether.dto.CommonResponse;
import com.gotogether.dto.request.SearchCondition;
import com.gotogether.dto.request.TogetherRequest;
import com.gotogether.service.TogetherService;
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
@RequestMapping("/api/together")
@RequiredArgsConstructor
public class TogetherController {

    @Autowired
    private final TogetherService togetherService;

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody TogetherRequest togetherRequest) throws Exception {
        return CommonResponse.toResponseEntity(togetherService.save(togetherRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{togetherId}")
    public ResponseEntity<?> update(@PathVariable("togetherId") Long togetherId, @Valid @RequestBody TogetherRequest togetherRequest) throws Exception {
        return CommonResponse.toResponseEntity(togetherService.update(togetherId, togetherRequest));
    }

    @PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{togetherId}")
    public ResponseEntity<?> delete(@PathVariable("togetherId") Long togetherId) throws Exception {
        togetherService.delete(togetherId);
        return CommonResponse.toResponseEntity();
    }

    @GetMapping("/{togetherId}")
    public ResponseEntity<?> get(@PathVariable("togetherId") Long togetherId) throws Exception {
        return CommonResponse.toResponseEntity(togetherService.get(togetherId));
    }

    @PostMapping("/list")
    public ResponseEntity<?> getPageList(@PageableDefault(size = 10, sort = "togetherId", direction = Sort.Direction.DESC)
                                         Pageable pageable,
                                         @Valid @RequestBody SearchCondition searchCondition) throws Exception {
        return CommonResponse.toResponseEntity(togetherService.getPageList(pageable, searchCondition));
    }
}
