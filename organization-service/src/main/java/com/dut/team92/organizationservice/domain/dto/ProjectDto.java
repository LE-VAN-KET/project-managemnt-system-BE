package com.dut.team92.organizationservice.domain.dto;

import com.dut.team92.common.enums.ProjectStatus;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProjectDto {
    private UUID id;

    private String name;
    private String description;
    private String domain;
    private Boolean isPublic;

    private ProjectDto parent;

    private UUID organizationId;

    private ProjectStatus projectStatus;
}
