package com.dokuny.mini_campus.admin.dto;

import com.dokuny.mini_campus.admin.repository.cond.MemberSearchCondition;
import lombok.*;

import static com.dokuny.mini_campus.admin.repository.cond.MemberSearchCondition.*;

@Data
public class MemberSearchRequest {
    private String searchType;
    private String searchValue;


    public MemberSearchCondition getSearchCondition() {
        MemberSearchConditionBuilder builder = builder();

        if (searchType.equals("email")) {
            builder.email(searchValue);
        } else if (searchType.equals("name")) {
            builder.name(searchValue);
        } else if (searchType.equals("phone")) {
            builder.phone(searchValue);
        }

        return builder.build();
    }
}
