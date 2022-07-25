package com.gotogether.dto.response;

import com.gotogether.entity.Note;
import com.gotogether.system.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Schema(description = "Note 응답")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoteResponse {
    private Long noteId;
    private String note;
    private String createdDate;
    private String modifiedDate;

    private String read;

    private String fromDeleted;

    private String toDeleted;

    private String from_username;

    private String from_nickname;

    private String to_username;

    private String to_nickname;

    public NoteResponse(Note note){
        this.noteId = note.getNoteId();
        this.note = note.getNote();
        this.createdDate = note.getCreatedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.modifiedDate = note.getModifiedDate().format(DateTimeFormatter.ofPattern(Constants.GLOBAL_DATETIME_FORMAT));
        this.read = note.getRead();
        this.fromDeleted = note.getFromDeleted();
        this.toDeleted = note.getToDeleted();
        this.from_username = note.getFromUser().getUsername();
        this.from_nickname = note.getFromUser().getNickname();
        this.to_username = note.getToUser().getUsername();
        this.to_nickname = note.getToUser().getNickname();
    }
}
