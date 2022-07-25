package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
@Schema(description = "Post 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @Schema(description = "Post Type", defaultValue = "TALK", allowableValues = {"TALK", "QA"})
    @NotBlank
    private String category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
