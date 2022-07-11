package com.dokuny.mini_campus.admin.service.impl;

import com.dokuny.mini_campus.admin.dto.MemberDetailDto;
import com.dokuny.mini_campus.admin.dto.MemberListDto;
import com.dokuny.mini_campus.admin.repository.AdminMemberRepository;
import com.dokuny.mini_campus.admin.repository.cond.MemberSearchCondition;
import com.dokuny.mini_campus.admin.service.AdminMemberService;
import com.dokuny.mini_campus.commons.dto.SearchInput;
import com.dokuny.mini_campus.member.entity.Member;
import com.dokuny.mini_campus.member.exception.MemberException;
import com.dokuny.mini_campus.member.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;


    @Transactional(readOnly = true)
    @Override
    public Page<MemberListDto> searchMembers(SearchInput request, Pageable pageable) {
        return adminMemberRepository.searchMembers(MemberSearchCondition.of(request), pageable);
    }

    @Transactional
    @Override
    public MemberDetailDto getMemberDetail(String id) {

        Member member = adminMemberRepository.findById(id)
                .orElseThrow(() -> new MemberException("존재하지 않는 계정입니다."));

        return MemberDetailDto.of(member);
    }

    @Transactional
    @Override
    public void editMemberDetail(MemberDetailDto dto) {
        Member member = adminMemberRepository
                .findById(dto.getEmail())
                .orElseThrow(() -> new MemberException("존재하지 않는 계정입니다."));
        member.changeStatus(MemberStatus.of(dto.getStatus()));

    }


}
