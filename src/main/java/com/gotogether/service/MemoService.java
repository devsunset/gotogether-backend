package com.gotogether.service;

import com.gotogether.dto.request.MemoRequest;
import com.gotogether.dto.response.MemoResponse;
import com.gotogether.entity.Memo;
import com.gotogether.entity.User;
import com.gotogether.repository.MemoRepository;
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
public class MemoService {
    @Autowired
    private final MemoRepository memoRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ModelMapper modelMapper;

    public Long save(MemoRequest memoRequest) throws Exception {
        Memo note = modelMapper.map(memoRequest, Memo.class);
        note.setRead(Constants.NO);
        note.setFromDeleted(Constants.NO);
        note.setToDeleted(Constants.NO);
        User user = authService.getSessionUser();
        note.setFromUser(user);
        note.setToUser(authService.getUser(memoRequest.getToUser()));
        if (user.getUsername().equals(memoRequest.getToUser())) {
            throw new CustomException(ErrorCode.NOT_SEND_NOTE_SELF);
        }
        return memoRepository.save(note).getMemoId();
    }

    public Long deleteFrom(Long memoId) throws Exception {
        User user = authService.getSessionUser();
        Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        if (!user.getUsername().equals(memo.getFromUser())) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }
        memo.setFromDeleted(Constants.YES);
        return memoRepository.save(memo).getMemoId();
    }

    public Long deleteTo(Long memoId) throws Exception {
        User user = authService.getSessionUser();
        Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!user.getUsername().equals(memo.getToDeleted())) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }
        memo.setToDeleted(Constants.YES);
        return memoRepository.save(memo).getMemoId();
    }

    public MemoResponse get(Long memoId) throws Exception {
        User user = authService.getSessionUser();
        Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(user.getUsername().equals(memo.getFromUser().getUsername())
                || user.getUsername().equals(memo.getToUser().getUsername()))) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }

        if (user.getUsername().equals(memo.getToUser().getUsername())) {
            if (Constants.NO.equals(memo.getRead())) {
                memo.setRead(Constants.YES);
                memoRepository.save(memo);
            }
        }
        return new MemoResponse(memo);
    }

    public HashMap<String, Long> getNewReceiveNote() throws Exception {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try {
            User user = authService.getSessionUser();
            result.put("MEMO", memoRepository.countByToUserAndReadAndToDeleted(user, "N", "N"));
        } catch (Exception e) {
            result.put("MEMO", 0L);
        }
        return result;
    }

    public Page<MemoResponse> getSendList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return memoRepository.findByFromUserAndFromDeleted(user, Constants.NO, pageable).map(MemoResponse::new);
    }

    public Page<MemoResponse> getReceiveList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return memoRepository.findByToUserAndToDeleted(user, Constants.NO, pageable).map(MemoResponse::new);
    }

}
