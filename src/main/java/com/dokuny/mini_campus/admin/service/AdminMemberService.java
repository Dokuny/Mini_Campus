package com.dokuny.mini_campus.admin.service;

import com.dokuny.mini_campus.admin.dto.MemberDetailDto;
import com.dokuny.mini_campus.admin.dto.MemberListDto;
import com.dokuny.mini_campus.admin.dto.MemberSearchInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminMemberService {

    Page<MemberListDto> searchMembers(MemberSearchInput input, Pageable pageable);

    MemberDetailDto getMemberDetail(String id);

    void editMemberDetail(MemberDetailDto dto);
}
