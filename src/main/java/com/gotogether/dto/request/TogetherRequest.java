package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Together 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TogetherRequest {

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    @Size(max = 120)
    private String togetherName;

    @NotBlank
    @Size(max = 20)
    private String category;

    @NotBlank
    @Size(max = 1000)
    private String purpose;

    @NotBlank
    private String content;

    @NotBlank
    @Size(max = 10)
    private String involveType;

    @Size(max = 120)
    private String openKakaoChat;

    @Size(max = 25)
    private String location;

    @Size(max = 25)
    private String latitude;

    @Size(max = 25)
    private String longitude;

    @NotNull
    private int maxMember;

    @NotNull
    private int currentMember;

    @NotBlank
    private String skill;
}
