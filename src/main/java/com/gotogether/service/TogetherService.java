package com.gotogether.service;

import com.gotogether.dto.request.SearchCondition;
import com.gotogether.dto.request.TogetherRequest;
import com.gotogether.dto.response.TogetherResponse;
import com.gotogether.entity.Together;
import com.gotogether.entity.User;
import com.gotogether.repository.TogetherRepository;
import com.gotogether.system.enums.ErrorCode;
import com.gotogether.system.exception.CustomException;
import com.gotogether.system.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TogetherService {
    @Autowired
    private final TogetherRepository togetherRepository;

    @Autowired
    private final AuthService authService;


    @Autowired
    private final ModelMapper modelMapper;

    public Long save(TogetherRequest togetherRequest) throws Exception {
        if (!(Utils.isValidTogetherCategory(togetherRequest.getCategory()))) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }

        if (!(Utils.isValidInvolveType(togetherRequest.getInvolveType()))) {
            throw new CustomException(ErrorCode.INVALID_INVOLVE_TYPE);
        }

        Together together = modelMapper.map(togetherRequest, Together.class);
        together.setWriter(authService.getSessionUser());
        return togetherRepository.save(together).getTogetherId();
    }

    public Long update(Long togetherId, TogetherRequest togetherRequest) throws Exception {
        if (!(Utils.isValidTogetherCategory(togetherRequest.getCategory()))) {
            throw new CustomException(ErrorCode.INVALID_CATEGORY);
        }

        if (!(Utils.isValidInvolveType(togetherRequest.getInvolveType()))) {
            throw new CustomException(ErrorCode.INVALID_INVOLVE_TYPE);
        }

        User user = authService.getSessionUser();
        Together orignal = togetherRepository.findById(togetherId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(orignal.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }

        Together together = modelMapper.map(togetherRequest, Together.class);
        together.setTogetherId(togetherId);
        together.setWriter(authService.getSessionUser());
        return togetherRepository.save(together).getTogetherId();
    }

    public void delete(Long togetherId) throws Exception {
        User user = authService.getSessionUser();
        Together post = togetherRepository.findById(togetherId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));

        if (!(Utils.isAdmin(user.getRoles()))) {
            if (!(user.getUsername().equals(post.getWriter().getUsername()))) {
                throw new CustomException(ErrorCode.NOT_WRITE_POST);
            }
        }
        togetherRepository.deleteById(togetherId);
    }

    public TogetherResponse get(Long togetherId) throws Exception {
        Together post = togetherRepository.findById(togetherId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_EXISTS_DATA));
        togetherRepository.updateHit(togetherId);
        return new TogetherResponse(post);
    }

    public Page<TogetherResponse> getPageList(Pageable pageable, SearchCondition searchCondition) throws Exception {
        if (searchCondition.getKeyword() != null && !"".equalsIgnoreCase(searchCondition.getKeyword().trim())) {
            return togetherRepository.findByTitleLikeIgnoreCaseOrContentLikeIgnoreCaseOrPurposeLikeIgnoreCaseOrCategoryLikeIgnoreCaseOrInvolveTypeLikeIgnoreCaseOrSkillLikeIgnoreCase(searchCondition.getKeyword(),  searchCondition.getKeyword(), searchCondition.getKeyword(), searchCondition.getKeyword(), searchCondition.getKeyword(), searchCondition.getKeyword(), pageable).map(TogetherResponse::new);
        } else {
            return togetherRepository.findAll(pageable).map(TogetherResponse::new);
        }
    }

    public List<TogetherResponse> getRecentList() throws Exception {
        List<Together> togethers = togetherRepository.findTop5ByTogetherIdGreaterThanOrderByTogetherIdDesc(0L);
        return togethers.stream().map(TogetherResponse::new).collect(Collectors.toList());
    }

}
