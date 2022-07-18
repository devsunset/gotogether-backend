package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.entity.Comment;
import com.gotogether.entity.Post;
import com.gotogether.repository.CommentRepository;
import com.gotogether.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService{

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final ModelMapper modelMapper;

    public Long save(CommentRequest commentRequest) throws Exception {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        log.debug("AS-IS : ################## : "+comment.toString());
        comment.setComment_id(null);
        Post post = postRepository.findById(commentRequest.getPost_id()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + commentRequest.getPost_id()));
        comment.setPost(post);
        comment.setWriter(authService.getSessionUser());
        log.debug("TO-BE : ################## : "+comment.toString());
        return commentRepository.save(comment).getComment_id();
    }

    public Long update(Long comment_id, CommentRequest commentRequest) throws Exception {
        Comment comment = modelMapper.map(commentRequest, Comment.class);
        comment.setWriter(authService.getSessionUser());
        return commentRepository.save(comment).getComment_id();
    }

    public void delete(Long comment_id) {
        commentRepository.deleteById(comment_id);
    }
}
