package com.dokuny.mini_campus.admin.controller;


import com.dokuny.mini_campus.admin.dto.MemberDetailDto;
import com.dokuny.mini_campus.admin.dto.MemberListDto;
import com.dokuny.mini_campus.admin.dto.MemberSearchRequest;
import com.dokuny.mini_campus.admin.service.AdminMemberService;
import com.dokuny.mini_campus.commons.dto.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping({"/admin/member/list.do"})
    public String memberSearch(Model model, MemberSearchRequest request,
                               @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<MemberListDto> page = adminMemberService.searchMembers(request, pageable);
        model.addAttribute("page", new Pagination(page));
        model.addAttribute("members", page.getContent());
        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do")
    public String memberDetail(Model model, @RequestParam("id") String email) {

        model.addAttribute("memberDetail"
                , adminMemberService.getMemberDetail(email));
        return "admin/member/member_detail";
    }

    @GetMapping("/admin/member/edit.do")
    public String editMember(Model model,MemberDetailDto dto) {
        adminMemberService.editMemberDetail(dto);
        return "redirect:/admin/member/detail.do?id="+dto.getEmail();
    }
}
