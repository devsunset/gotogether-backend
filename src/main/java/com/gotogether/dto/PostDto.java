package com.gotogether.dto;

import com.gotogether.entity.BaseEntity;
import com.gotogether.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto extends BaseEntity {
    private String category;
    private String title;
    private String content;
    private boolean deleted;
    private int hit;
    private User writer;
    @Builder
    public PostDto(String category, String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
