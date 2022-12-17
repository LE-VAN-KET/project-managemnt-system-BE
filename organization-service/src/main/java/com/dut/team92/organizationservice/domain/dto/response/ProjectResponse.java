package com.dut.team92.organizationservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse implements Serializable {
    private UUID projectId;
    private UUID userId;
    private boolean isAdminOrganization;
}