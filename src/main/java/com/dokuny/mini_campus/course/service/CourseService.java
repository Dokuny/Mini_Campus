package com.dokuny.mini_campus.course.service;

import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.commons.dto.ResponseResult;
import com.dokuny.mini_campus.course.dto.CourseInput;
import com.dokuny.mini_campus.course.dto.CourseDto;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import com.dokuny.mini_campus.course.dto.TakeCourseInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    boolean add(CourseInput input);

    Page<CourseListDto> list(SearchInput input, Pageable pageable);

    CourseDto find(Long id);

    boolean edit(CourseInput input);

    boolean delete(String idList);

    Page<CourseDto> frontList(SearchInput input,Pageable pageable);

    ResponseResult req(TakeCourseInput input);
}
