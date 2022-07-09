package com.dokuny.mini_campus.admin.dto;

import com.dokuny.mini_campus.member.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberDetailDto {

    private String email;
    private String name;
    private String phone;
    private LocalDateTime registeredAt;
    private String role;
    private String status;


    public static MemberDetailDto of(Member member) {
        return MemberDetailDto.builder()
                .email(member.getId())
                .name(member.getName())
                .phone(member.getPhone())
                .registeredAt(member.getCreatedAt())
                .role(member.getRole().name())
                .status(member.getStatus().name())
                .build();
    }

}
