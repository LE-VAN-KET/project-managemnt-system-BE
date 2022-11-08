package com.dut.team92.organizationservice.domain.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CreateOrganizationResponse {
    private UUID organizationId;

    private String name;
    private String description;
    private Boolean isDelete;

    private String logo;

    private UUID userId;
}
