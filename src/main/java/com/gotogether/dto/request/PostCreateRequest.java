package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
@Schema(description = "Post 생성 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @Schema(description = "Post 유형", defaultValue = "TALK", allowableValues = {"TALK", "QA"})
    @NotBlank
    private String category;

    @Schema(description = "Post 제목")
    @NotBlank
    private String title;

    @Schema(description = "Post 내용")
    @NotBlank
    private String content;
}
