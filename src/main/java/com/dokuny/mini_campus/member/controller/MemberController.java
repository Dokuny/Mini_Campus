package com.dokuny.mini_campus.member.controller;

import com.dokuny.mini_campus.member.dto.FindPasswordRequest;
import com.dokuny.mini_campus.member.dto.MemberRegisterRequest;
import com.dokuny.mini_campus.member.dto.ResetPasswordRequest;
import com.dokuny.mini_campus.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("request", MemberRegisterRequest.builder().build());
        return "member/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid @ModelAttribute("request") MemberRegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        memberService.register(request);
        return "member/register_complete";
    }

    @GetMapping("/register/email-auth")
    public String emailAuth(@RequestParam("authKey") String authKey) {
        memberService.activateAuthEmail(authKey);
        return "redirect:/member/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/find/password")
    public String findPasswordForm() {
        return "member/find_password";
    }

    @PostMapping("/find/password")
    public String findPasswordSubmit(@Valid FindPasswordRequest request) {
        memberService.findPassword(request.getId(), request.getName());
        return "member/find_password_result";
    }


    @GetMapping("/reset/password")
    public String resetPasswordForm(@RequestParam("passwordKey") String passwordKey, Model model) {
        model.addAttribute("passwordKey", passwordKey);
        model.addAttribute("request", new ResetPasswordRequest());
        return "member/reset_password";
    }

    @PostMapping("/reset/password")
    public String resetPasswordSubmit(Model model,@Valid @ModelAttribute("request") ResetPasswordRequest request,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordKey", request.getPasswordKey());
            return "member/reset_password";
        }

        memberService.resetPassword(request.getPasswordKey(), request.getPassword());

        return "member/reset_password_result";
    }
}
