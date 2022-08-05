package com.gotogether.dto.response;

import com.gotogether.entity.TogetherComment;
import com.gotogether.system.constants.Constants;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TogetherCommentResponse {
    private Long togetherCommentId;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;
    private Long togetherId;

    public TogetherCommentResponse(TogetherComment togetherComment) {
        this.togetherCommentId = togetherComment.getTogetherCommentId();
        this.content = togetherComment.getContent();
        this.createdDate = togetherComment.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = togetherComment.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = togetherComment.getWriter().getNickname();
        this.username = togetherComment.getWriter().getUsername();
        this.togetherId = togetherComment.getTogether().getTogetherId();
    }
}
