package com.dokuny.mini_campus.course.service;

import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.course.dto.TakeCourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TakeCourseService {

    Page<TakeCourseDto> list(SearchInput input, Pageable pageable);

    boolean updateStatus(Long id, String status);
}
