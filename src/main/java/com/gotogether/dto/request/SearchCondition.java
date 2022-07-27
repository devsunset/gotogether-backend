package com.gotogether.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "검색 조건")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCondition {

    @Schema(description = "Post Type")
    private String category;

    @Schema(description = "검색어")
    private String keyword;
}
