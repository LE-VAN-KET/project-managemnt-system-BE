package com.dut.team92.organizationservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckOrganizationExistResponse {
    private boolean isExistOrganization;
}
