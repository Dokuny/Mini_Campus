package com.dokuny.mini_campus.admin.repository.cond;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberSearchCondition {
    private String email;
    private String name;
    private String phone;
}
