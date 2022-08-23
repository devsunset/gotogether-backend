package com.gotogether.dto.response;

import com.gotogether.entity.Together;
import com.gotogether.system.constants.Constants;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TogetherResponse {
    private Long togetherId;
    private String title;
    private String category;
    private String content;
    private String involveType;
    private String openKakaoChat;
    private String latitude;
    private String longitude;
    private int hit;
    private int currentMember;
    private int maxMember;
    private int progress;

    private String progressLegend;
    private String skill;
    private int togetherComment_count;
    private String createdDate;
    private String modifiedDate;
    private String nickname;
    private String username;

    public TogetherResponse(Together together) {
        this.togetherId = together.getTogetherId();
        this.title = together.getTitle();
        this.category = together.getCategory();
        this.content = together.getContent();
        this.involveType = together.getInvolveType();
        this.openKakaoChat = together.getOpenKakaoChat();
        this.latitude = together.getLatitude();
        this.longitude = together.getLongitude();
        this.hit = together.getHit();
        this.maxMember = together.getMaxMember();
        this.currentMember = together.getCurrentMember();
        this.skill = together.getSkill();
        this.togetherComment_count = together.getTogetherComments().size();
        this.createdDate = together.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = together.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.nickname = together.getWriter().getNickname();
        this.username = together.getWriter().getUsername();

        if (together.getMaxMember() == 0 || together.getCurrentMember() == 0) {
            this.progress = 0;
            this.progressLegend = "danger";
        } else {
            float temp = (((float) together.getCurrentMember() / (float) together.getMaxMember()) * 100);
            this.progress = Math.round(temp);
            // progress 0~25 : danger   26~50 :  warning  51~75 :  primary  76~ 100  :  success
            if (this.progress > 0 && this.progress <= 25) {
                this.progressLegend = "danger";
            } else if (this.progress > 25 && this.progress <= 50) {
                this.progressLegend = "warning";
            } else if (this.progress > 50 && this.progress <= 75) {
                this.progressLegend = "primary";
            } else {
                this.progressLegend = "success";
            }
        }
    }
}
