package com.gotogether.dto.request;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TogetherRequest {

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    @Size(max = 20)
    private String category;

    @NotBlank
    private String content;

    @NotBlank
    @Size(max = 10)
    private String involveType;

    @Size(max = 120)
    private String openKakaoChat;

    @Size(max = 25)
    private String latitude;

    @Size(max = 25)
    private String longitude;

    @NotNull
    @Max(100)
    @Min(2)
    private int maxMember;

    @NotNull
    @Max(100)
    @Min(1)
    private int currentMember;

    @NotBlank
    private String skill;
}
