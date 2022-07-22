package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Schema(description = "Note 요청")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteRequest {

    @Schema(description = "Note 내용")
    @NotBlank
    private String note;

    @Schema(description = "수신자")
    @NotBlank
    private String toUser;
}
