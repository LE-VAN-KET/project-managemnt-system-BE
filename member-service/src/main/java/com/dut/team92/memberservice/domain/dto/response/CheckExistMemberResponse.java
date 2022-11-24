package com.dut.team92.memberservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckExistMemberResponse {
    private Boolean isExistMember;
}
