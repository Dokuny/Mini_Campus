package com.dokuny.mini_campus.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {


    @RequestMapping("/errors")
    public String errorPage() {
        return "commons/error";
    }
}
