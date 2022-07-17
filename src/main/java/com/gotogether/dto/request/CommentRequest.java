package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Comment 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

    @Schema(description = "post_id")
    @NotNull
    private Long post_id;

    @Schema(description = "Comment 내용")
    @NotBlank
    private String content;
}
