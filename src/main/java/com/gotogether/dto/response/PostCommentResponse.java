package com.gotogether.dto.response;

import com.gotogether.entity.PostComment;
import com.gotogether.system.constants.Constants;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentResponse {
    private Long postCommentId;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;
    private Long postId;

    public PostCommentResponse(PostComment postComment) {
        this.postCommentId = postComment.getPostCommentId();
        this.content = postComment.getContent();
        this.createdDate = postComment.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = postComment.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = postComment.getWriter().getNickname();
        this.username = postComment.getWriter().getUsername();
        this.postId = postComment.getPost().getPostId();
    }
}
