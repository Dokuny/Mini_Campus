package com.dokuny.mini_campus.course.controller;

import com.dokuny.mini_campus.commons.dto.ResponseResult;
import com.dokuny.mini_campus.course.dto.TakeCourseInput;
import com.dokuny.mini_campus.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiCourseController {

    private final CourseService courseService;

    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(@RequestBody TakeCourseInput input, Principal principal) {

        input.setUserId(principal.getName());

        ResponseResult result = courseService.req(input);

        return ResponseEntity.ok().body(result);
    }
}
