package com.gotogether.service;

import com.gotogether.dto.request.CommentRequest;
import com.gotogether.dto.request.PostRequest;
import com.gotogether.dto.request.PostSearchCondition;
import com.gotogether.dto.response.PostResponse;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final CommentService commentService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(PostRequest postRequest) throws Exception {
        if(!(Constants.PostType.TALK.equals(postRequest.getCategory()) || Constants.PostType.QA.equals(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }
        Post post = modelMapper.map(postRequest, Post.class);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPostId();
    }

    public Long update(Long postId, PostRequest postRequest) throws Exception {
        if(!(Utils.isValidPostType(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }
        User user = authService.getSessionUser();
        Post orignal = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!(Utils.isAdmin(user.getRoles()))){
            if(!(user.getUsername().equals(orignal.getWriter().getUsername()))){
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Post post = modelMapper.map(postRequest, Post.class);
        post.setPostId(postId);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPostId();
    }

    public Long changecategory(Long postId, PostRequest postRequest) throws Exception {
        if(!(Utils.isValidPostType(postRequest.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }

        User user = authService.getSessionUser();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(post.getCategory().equals(postRequest.getCategory())){
            throw new CustomException(ErrorCode.NOT_CHANG_EQUAL_CATEGEORY);
        }

        if(!(Utils.isAdmin(user.getRoles()))){
            throw new CustomException(ErrorCode.NOT_ROLE_ADMIN);
        }

        post.setCategory(postRequest.getCategory());
        Long postId_result = postRepository.save(post).getPostId();

        //Comment Service
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPostId(postId);

        String content = "";
        if(Constants.PostType.TALK.equals(postRequest.getCategory())){
            content = "QA 게시판 에서 TALK 게시판으로 "+user.getNickname()+"님 (관리자)에 의해 이동 되었습니다.";
        }else{
            content = "TALK 게시판 에서  QA 게시판으로 "+user.getNickname()+"님 (관리자)에 의해 이동 되었습니다.";
        }
        commentRequest.setContent(content);
        commentService.save(commentRequest);

        return postId_result;
    }

    public void delete(Long postId) throws Exception {
        User user = authService.getSessionUser();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));

        if(!(Utils.isAdmin(user.getRoles()))){
            if(!(user.getUsername().equals(post.getWriter().getUsername()))){
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }
        postRepository.deleteById(postId);
    }

    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_POST));
        postRepository.updateHit(postId);
        return new PostResponse(post);
    }

    public Page<PostResponse> getList(Pageable pageable, PostSearchCondition postSearchCondition) {
        if(!(Utils.isValidPostType(postSearchCondition.getCategory()))){
            throw new CustomException(ErrorCode.NOT_POST_TYPE);
        }
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "postId"));

        return postRepository.findByCategory(postSearchCondition.getCategory(), pageable).map(PostResponse::new);
    }
}
