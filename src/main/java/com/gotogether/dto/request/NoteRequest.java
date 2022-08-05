package com.gotogether.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteRequest {

    @NotBlank
    private String note;

    @NotBlank
    private String toUser;
}
