package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Note 검색 조건")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteSearchCondition {

    @Schema(description = "검색 타입")
    private String keywordType;

    @Schema(description = "검색어")
    private String keyword;
}
