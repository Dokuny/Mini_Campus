package com.dokuny.mini_campus.course.service;

import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.course.dto.CourseAddInput;
import com.dokuny.mini_campus.course.dto.CourseDto;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    boolean add(CourseAddInput input);

    Page<CourseListDto> list(SearchInput input, Pageable pageable);

    CourseDto find(Long id);
}
