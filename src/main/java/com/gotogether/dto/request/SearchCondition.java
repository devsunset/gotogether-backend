package com.gotogether.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCondition {

    private String category;

    private String keyword;
}
