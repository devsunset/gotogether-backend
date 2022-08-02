package com.gotogether.dto.response;

import com.gotogether.entity.Together;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Schema(description = "Together 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TogetherResponse {
    private Long togetherId;
    private String title;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;
    private int hit;
    private int togetherComment_count;

    public TogetherResponse(Together together) {
        this.togetherId = together.getTogetherId();
        this.title = together.getTitle();
        this.content = together.getContent();
        this.createdDate = together.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = together.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = together.getWriter().getNickname();
        this.username = together.getWriter().getUsername();
        this.hit = together.getHit();
        this.togetherComment_count = together.getTogetherComments().size();
    }
}
