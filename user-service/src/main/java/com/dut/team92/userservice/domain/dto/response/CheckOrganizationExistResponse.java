package com.dut.team92.userservice.domain.dto.response;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class CheckOrganizationExistResponse extends CommonErrorResponse {
    private Boolean isExistOrganization;
}
