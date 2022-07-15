package com.gotogether.payload.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {
    @NotBlank
    private String category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
