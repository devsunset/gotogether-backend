package com.gotogether.dto.response;

import com.gotogether.entity.Comment;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Schema(description = "Comment 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
    private Long comment_id;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;
    private Long post_id;


    /* Entity -> Response*/
    public CommentResponse(Comment comment) {
        this.comment_id = comment.getComment_id();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = comment.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = comment.getWriter().getNickname();
        this.username = comment.getWriter().getUsername();
        this.post_id = comment.getPost().getPost_id();
    }
}
