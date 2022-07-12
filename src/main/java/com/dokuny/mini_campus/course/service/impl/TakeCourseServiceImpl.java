package com.dokuny.mini_campus.course.service.impl;

import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.course.dto.TakeCourseDto;
import com.dokuny.mini_campus.course.entity.TakeCourse;
import com.dokuny.mini_campus.course.exception.TakeCourseNotExistException;
import com.dokuny.mini_campus.course.repository.TakeCourseRepository;
import com.dokuny.mini_campus.course.repository.cond.TakeCourseSearchCondition;
import com.dokuny.mini_campus.course.service.TakeCourseService;
import com.dokuny.mini_campus.course.type.TakeCourseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {

    private final TakeCourseRepository takeCourseRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<TakeCourseDto> list(SearchInput input, Pageable pageable) {
        return takeCourseRepository.search(TakeCourseSearchCondition.of(input), pageable);
    }

    @Transactional
    @Override
    public boolean updateStatus(Long id, String status) {

        TakeCourse takeCourse = takeCourseRepository.findById(id)
                .orElseThrow(() -> new TakeCourseNotExistException("수강신청정보가 존재하지 않습니다."));

        takeCourse.setStatus(TakeCourseStatus.valueOf(status));

        return true;
    }
}
