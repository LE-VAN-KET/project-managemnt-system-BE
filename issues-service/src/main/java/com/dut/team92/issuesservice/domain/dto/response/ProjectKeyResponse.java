package com.dut.team92.issuesservice.domain.dto.response;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProjectKeyResponse extends CommonErrorResponse {
    private String key;
}
