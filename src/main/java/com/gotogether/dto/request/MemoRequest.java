package com.gotogether.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoRequest {

    @NotBlank
    private String memo;

    @NotBlank
    private String receiver;
}
