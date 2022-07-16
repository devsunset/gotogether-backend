package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Schema(description = "Comment 생성 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

    @Schema(description = "post_id")
    private Long post_id;

    @Column(name = "comment_id")
    private Long comment_id;

    @Schema(description = "Comment 내용")
    @NotBlank
    private String content;
}
