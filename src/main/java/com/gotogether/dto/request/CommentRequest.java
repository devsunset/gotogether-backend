package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Comment 요청")
@Getter
@Setter
@ToString
public class CommentRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String content;
}
