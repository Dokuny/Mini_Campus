package com.dokuny.mini_campus.course.controller;


import com.dokuny.mini_campus.admin.dto.CategoryDto;
import com.dokuny.mini_campus.admin.service.CategoryService;
import com.dokuny.mini_campus.commons.dto.Pagination;
import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.course.dto.CourseAddInput;

import com.dokuny.mini_campus.course.dto.CourseDto;
import com.dokuny.mini_campus.course.dto.CourseListDto;
import com.dokuny.mini_campus.course.service.CourseService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping({"/admin/course/list.do"})
    public String list(Model model, SearchInput input,
                       @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<CourseListDto> list = courseService.list(input, pageable);
        model.addAttribute("page", new Pagination(list));
        model.addAttribute("list", list.getContent());
        return "admin/course/list";
    }

    @GetMapping("/admin/course/add.do")
    public String addForm(Model model) {

        model.addAttribute("categories",categoryService.list());
        return "admin/course/add";
    }

    @PostMapping("/admin/course/add.do")
    public String add(Model model, CourseAddInput input) {

        courseService.add(input);

        return "redirect:/admin/course/list.do";
    }



    @GetMapping("/admin/course/edit.do")
    public String editForm(Model model,@RequestParam("id") Long id) {

        CourseDto courseDto = courseService.find(id);

        model.addAttribute("course", courseDto);

        return "admin/course/edit";
    }




}
