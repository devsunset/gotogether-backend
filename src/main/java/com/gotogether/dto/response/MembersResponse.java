package com.gotogether.dto.response;

import com.gotogether.entity.User;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Schema(description = "Member 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MembersResponse {
    private String username;
    private String nickname;
    private String introduce;
    private String note;
    private String github;
    private String homepage;
    private String profileImageLink;
    private String createdDate;
    private String modifiedDate;

    private List<HashMap<String, String>> skills;

    public MembersResponse(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.createdDate = user.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = user.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
    }
}
