package com.dut.team92.organizationservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CheckProjectExistResponse{
    private Boolean isExistProject;
}
