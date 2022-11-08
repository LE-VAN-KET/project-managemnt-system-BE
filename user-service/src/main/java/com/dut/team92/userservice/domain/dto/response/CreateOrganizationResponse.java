package com.dut.team92.userservice.domain.dto.response;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CreateOrganizationResponse extends CommonErrorResponse {
    private UUID organizationId;

    private String name;
    private String description;
    private Boolean isDelete;

    private String logo;

    private UUID userId;
}
