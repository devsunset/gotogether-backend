package com.gotogether.dto.response;

import com.gotogether.entity.UserInfo;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Schema(description = "UserInfo 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponse {

    private Long userInfoId;
    private String introduce;
    private String note;
    private String github;
    private String homepage;
    private String profileImageLink;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;

    public UserInfoResponse(UserInfo userInfo) {
        this.userInfoId = userInfo.getUserInfoId();
        this.introduce = userInfo.getIntroduce();
        this.note = userInfo.getNote();
        this.github = userInfo.getGithub();
        this.homepage = userInfo.getHomepage();
        this.profileImageLink = userInfo.getProfileImageLink();
        this.createdDate = userInfo.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = userInfo.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = userInfo.getUser().getNickname();
        this.username = userInfo.getUser().getUsername();
    }
}
