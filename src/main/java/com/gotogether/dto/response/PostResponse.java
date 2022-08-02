package com.gotogether.dto.response;

import com.gotogether.entity.Post;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Schema(description = "Post 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {
    private Long postId;
    private String category;
    private String title;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;
    private int hit;
    private int comment_count;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = post.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = post.getWriter().getNickname();
        this.username = post.getWriter().getUsername();
        this.hit = post.getHit();
        this.comment_count = post.getComments().size();
    }
}
