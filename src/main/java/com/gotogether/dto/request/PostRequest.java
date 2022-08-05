package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @Schema(description = "Post Type", defaultValue = "TALK", allowableValues = {"TALK", "QA"})
    @NotBlank
    private String category;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    private String content;
}
