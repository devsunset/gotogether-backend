package com.gotogether.service;

import com.gotogether.dto.request.TogetherCommentRequest;
import com.gotogether.dto.response.TogetherCommentResponse;
import com.gotogether.entity.Together;
import com.gotogether.entity.TogetherComment;
import com.gotogether.entity.User;
import com.gotogether.repository.TogetherCommentRepository;
import com.gotogether.repository.TogetherRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TogetherCommentService {
    @Autowired
    private final TogetherCommentRepository togetherCommentRepository;

    @Autowired
    private final AuthService authService;
    @Autowired
    private final TogetherRepository togetherRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(TogetherCommentRequest togetherCommentRequest) throws Exception {
        TogetherComment togetherComment = modelMapper.map(togetherCommentRequest, TogetherComment.class);
        togetherComment.setCommentId(null);
        togetherComment.setWriter(authService.getSessionUser());
        return togetherCommentRepository.save(togetherComment).getCommentId();
    }

    public Long update(Long togetherCommentId, TogetherCommentRequest togetherCommentRequest) throws Exception {
        User user = authService.getSessionUser();
        TogetherComment orignal = togetherCommentRepository.findById(togetherCommentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        TogetherComment togetherComment = modelMapper.map(togetherCommentRequest, TogetherComment.class);
        togetherComment.setCommentId(togetherCommentId);
        togetherComment.setWriter(user);
        return togetherCommentRepository.save(togetherComment).getCommentId();
    }

    public void delete(Long togetherCommentId) throws Exception {
        User user = authService.getSessionUser();
        TogetherComment orignal = togetherCommentRepository.findById(togetherCommentId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        togetherCommentRepository.deleteById(togetherCommentId);
    }

    public List<TogetherCommentResponse> getList(Long togetherId) throws Exception {
        Together together = togetherRepository.findById(togetherId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        List<TogetherComment> togetherComments = together.getTogetherComments();
        return togetherComments.stream().map(TogetherCommentResponse::new).collect(Collectors.toList());
    }
}
