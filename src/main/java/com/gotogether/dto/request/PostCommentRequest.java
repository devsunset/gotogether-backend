package com.gotogether.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PostCommentRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String content;
}
