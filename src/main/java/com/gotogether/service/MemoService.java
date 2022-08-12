package com.gotogether.service;

import com.gotogether.dto.request.CommonRequest;
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
        note.setReadflag(Constants.NO);
        note.setSdelete(Constants.NO);
        note.setRdelete(Constants.NO);
        User user = authService.getSessionUser();
        note.setSender(user);
        note.setReceiver(authService.getUser(memoRequest.getReceiver()));
        if (user.getUsername().equals(memoRequest.getReceiver())) {
            throw new CustomException(ErrorCode.NOT_SEND_NOTE_SELF);
        }
        return memoRepository.save(note).getMemoId();
    }

    public void deleteSend(CommonRequest commonRequest) throws Exception {
        User user = authService.getSessionUser();
        if (commonRequest.getIdSeparatorValues() != null &&!commonRequest.getIdSeparatorValues().trim().equals("")) {
            String[] memoIds = commonRequest.getIdSeparatorValues().split(Constants.SEPARATOR_COMMA);
            for (String el : memoIds) {
                Long memoId = Long.parseLong(el);
                Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_EXISTS_DATA));
                if (!user.getUsername().equals(memo.getSender().getUsername())) {
                    throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
                }
                memo.setSdelete(Constants.YES);
                memoRepository.save(memo);
            }
        }
    }

    public void deleteReceive(CommonRequest commonRequest) throws Exception {
        User user = authService.getSessionUser();
        if (commonRequest.getIdSeparatorValues() != null && !commonRequest.getIdSeparatorValues().trim().equals("")) {
            String[] memoIds = commonRequest.getIdSeparatorValues().split(Constants.SEPARATOR_COMMA);
            for (String el : memoIds) {
                Long memoId = Long.parseLong(el);
                Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                        new CustomException(ErrorCode.NOT_EXISTS_DATA));
                if (!user.getUsername().equals(memo.getReceiver().getUsername())) {
                    throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
                }
                memo.setRdelete(Constants.YES);
                memoRepository.save(memo);
            }
        }
    }

    public void updateRead(Long memoId) throws Exception {
        User user = authService.getSessionUser();
        Memo memo = memoRepository.findById(memoId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(user.getUsername().equals(memo.getSender().getUsername())
                || user.getUsername().equals(memo.getReceiver().getUsername()))) {
            throw new CustomException(ErrorCode.NOT_AUTH_NOTE);
        }

        if (user.getUsername().equals(memo.getReceiver().getUsername())) {
            if (Constants.NO.equals(memo.getReadflag())) {
                memo.setReadflag(Constants.YES);
                memoRepository.save(memo);
            }
        }
    }

    public HashMap<String, Long> getNewReceiveMemo() throws Exception {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try {
            User user = authService.getSessionUser();
            result.put("MEMO", memoRepository.countByReceiverAndReadflagAndRdelete(user, Constants.NO, Constants.NO));
        } catch (Exception e) {
            result.put("MEMO", 0L);
        }
        return result;
    }

    public Page<MemoResponse> getSendList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return memoRepository.findBySenderAndSdelete(user, Constants.NO, pageable).map(MemoResponse::new);
    }

    public Page<MemoResponse> getReceiveList(Pageable pageable) throws Exception {
        User user = authService.getSessionUser();
        return memoRepository.findByReceiverAndRdelete(user, Constants.NO, pageable).map(MemoResponse::new);
    }

}
