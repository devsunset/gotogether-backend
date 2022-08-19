package com.gotogether.service;

import com.gotogether.dto.request.PostCommentRequest;
import com.gotogether.dto.request.PostRequest;
import com.gotogether.dto.request.SearchCondition;
import com.gotogether.dto.response.PostResponse;
import com.gotogether.entity.Post;
import com.gotogether.entity.User;
import com.gotogether.repository.PostRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.enums.PostCategory;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PostCommentService commentService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(PostRequest postRequest) throws Exception {
        if (!(Utils.isValidPostCategory(postRequest.getCategory()))) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }
        Post post = modelMapper.map(postRequest, Post.class);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPostId();
    }

    public Long update(Long postId, PostRequest postRequest) throws Exception {
        if (!(Utils.isValidPostCategory(postRequest.getCategory()))) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }
        User user = authService.getSessionUser();
        Post orignal = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Post post = modelMapper.map(postRequest, Post.class);
        post.setPostId(postId);
        post.setWriter(authService.getSessionUser());
        return postRepository.save(post).getPostId();
    }

    public String changeCategory(Long postId) throws Exception {
        User user = authService.getSessionUser();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }

        String category = "";
        if(PostCategory.TALK.getName().equals(post.getCategory())){
            category = PostCategory.QA.getName();
        }else{
            category = PostCategory.TALK.getName();
        }

        post.setCategory(category);

        Long postId_result = postRepository.save(post).getPostId();

        //Comment Service
        PostCommentRequest commentRequest = new PostCommentRequest();
        commentRequest.setPostId(postId);

        String content = "";
        if (PostCategory.TALK.getName().equals(category)) {
            content = "QA ---&gt; TALK 게시판으로 " + user.getNickname() + "님 (관리자)에 의해 이동 되었습니다.";
        } else {
            content = "TALK ---&gt; QA 게시판으로 " + user.getNickname() + "님 (관리자)에 의해 이동 되었습니다.";
        }
        commentRequest.setContent(content);
        commentService.save(commentRequest);

        return category;
    }

    public void delete(Long postId) throws Exception {
        User user = authService.getSessionUser();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(post.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }
        postRepository.deleteById(postId);
    }

    public PostResponse get(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        postRepository.updateHit(postId);
        return new PostResponse(post);
    }

    public Page<PostResponse> getPageList(Pageable pageable, SearchCondition searchCondition) throws Exception {
        if (!(Utils.isValidPostCategory(searchCondition.getCategory()))) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }

        if (searchCondition.getKeyword() != null && !"".equalsIgnoreCase(searchCondition.getKeyword().trim())) {
            return postRepository.findByCategoryAndTitleContainsIgnoreCaseOrCategoryAndContentContainsIgnoreCase(searchCondition.getCategory(), searchCondition.getKeyword(), searchCondition.getCategory(), searchCondition.getKeyword(), pageable).map(PostResponse::new);
        } else {
            return postRepository.findByCategory(searchCondition.getCategory(), pageable).map(PostResponse::new);
        }
    }
}
