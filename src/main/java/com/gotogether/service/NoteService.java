package com.gotogether.service;

import com.gotogether.dto.request.NoteRequest;
import com.gotogether.dto.response.NoteResponse;
import com.gotogether.entity.Note;
import com.gotogether.entity.User;
import com.gotogether.repository.NoteRepository;
import com.gotogether.system.constants.Constants;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private final NoteRepository noteRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(NoteRequest noteRequest) throws Exception {
        Note note = modelMapper.map(noteRequest, Note.class);
        note.setRead(Constants.NO);
        note.setFromDeleted(Constants.NO);
        note.setToDeleted(Constants.NO);
        User user = authService.getSessionUser();
        note.setFromUser(user);
        note.setToUser(authService.getUser(noteRequest.getToUser()));
        if (user.getUsername().equals(noteRequest.getToUser())) {
            throw new CustomException(ErrorCode.NOT_SEND_NOTE_SELF);
        }
        return noteRepository.save(note).getNoteId();
    }

    public Long deleteFrom(Long noteId) throws Exception {
        User user = authService.getSessionUser();
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        if (!user.getUsername().equals(note.getFromUser())) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }
        note.setFromDeleted(Constants.YES);
        return noteRepository.save(note).getNoteId();
    }

    public Long deleteTo(Long noteId) throws Exception {
        User user = authService.getSessionUser();
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!user.getUsername().equals(note.getToDeleted())) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }
        note.setToDeleted(Constants.YES);
        return noteRepository.save(note).getNoteId();
    }

    public NoteResponse get(Long noteId) throws Exception {
        User user = authService.getSessionUser();
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(user.getUsername().equals(note.getFromUser().getUsername())
                || user.getUsername().equals(note.getToUser().getUsername()))) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }

        if (user.getUsername().equals(note.getToUser().getUsername())) {
            if (Constants.NO.equals(note.getRead())) {
                note.setRead(Constants.YES);
                noteRepository.save(note);
            }
        }
        return new NoteResponse(note);
    }

    public HashMap<String, Long> getNewReceiveNote() throws Exception {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            User user = authService.getSessionUser();
            result.put("NOTE", noteRepository.countByToUserAndReadAndToDeleted(user, "N", "N"));
        }catch(Exception e){
            result.put("NOTE", 0L);
        }
        return result;
    }

    public Page<NoteResponse> getSendList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return noteRepository.findByFromUserAndFromDeleted(user, Constants.NO, pageable).map(NoteResponse::new);
    }

    public Page<NoteResponse> getReceiveList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return noteRepository.findByToUserAndToDeleted(user, Constants.NO, pageable).map(NoteResponse::new);
    }

}
