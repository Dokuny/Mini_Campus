package com.dokuny.mini_campus.course.service.impl;

import com.dokuny.mini_campus.admin.entity.Category;
import com.dokuny.mini_campus.admin.exception.CategoryNotExistException;
import com.dokuny.mini_campus.admin.repository.CategoryRepository;
import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.course.dto.CourseInput;
import com.dokuny.mini_campus.course.dto.CourseDto;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import com.dokuny.mini_campus.course.entity.Course;
import com.dokuny.mini_campus.course.exception.CourseNotExistException;
import com.dokuny.mini_campus.course.repository.CourseRepository;
import com.dokuny.mini_campus.course.repository.cond.CourseSearchCondition;
import com.dokuny.mini_campus.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public boolean add(CourseInput input) {

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException("존재하지 않는 카테고리입니다."));


        courseRepository.save(Course.builder()
                .subject(input.getSubject())
                .category(category)
                .contents(input.getContents())
                .imagePath(input.getImagePath())
                .price(input.getPrice())
                .salePrice(input.getSalePrice())
                .saleEndAt(LocalDateTime.parse(input.getSaleEndAt()))
                .keyword(input.getKeyword())
                .summary(input.getSummary())
                .build());

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CourseListDto> list(SearchInput input, Pageable pageable) {
        return courseRepository.search(CourseSearchCondition.of(input), pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public CourseDto find(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotExistException("존재하지 않는 강의입니다."));
        return CourseDto.of(course);
    }

    @Transactional
    @Override
    public boolean edit(CourseInput input) {
        Course course = courseRepository.findById(input.getId())
                .orElseThrow(() -> new CourseNotExistException("존재하지 않는 강의입니다."));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() -> new CategoryNotExistException("존재하지 않는 카테고리입니다."));

        course.setCategory(category);
        course.setImagePath(input.getImagePath());
        course.setKeyword(input.getKeyword());
        course.setSubject(input.getSubject());
        course.setSummary(input.getSummary());
        course.setContents(input.getContents());
        course.setPrice(input.getPrice());
        course.setSalePrice(input.getSalePrice());
        course.setSaleEndAt(LocalDateTime.parse(input.getSaleEndAt()));

        return true;
    }


}
