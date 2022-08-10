package com.gotogether.dto.response;

import com.gotogether.entity.Memo;
import com.gotogether.system.constants.Constants;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoResponse {
    private Long memoId;
    private String memo;
    private String createdDate;
    private String modifiedDate;

    private String readflag;

    private String sdelete;

    private String rdelete;

    private String senderUsername;

    private String senderNickname;

    private String receiverUsername;

    private String receiverNickname;

    public MemoResponse(Memo memo) {
        this.memoId = memo.getMemoId();
        this.memo = memo.getMemo();
        this.createdDate = memo.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = memo.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.readflag = memo.getReadflag();
        this.sdelete = memo.getSdelete();
        this.rdelete = memo.getRdelete();
        this.senderUsername = memo.getSender().getUsername();
        this.senderNickname = memo.getSender().getNickname();
        this.receiverUsername = memo.getReceiver().getUsername();
        this.receiverNickname = memo.getReceiver().getNickname();
    }
}
