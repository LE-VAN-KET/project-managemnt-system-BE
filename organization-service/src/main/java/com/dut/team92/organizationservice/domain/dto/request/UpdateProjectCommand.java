package com.dut.team92.organizationservice.domain.dto.request;

import com.dut.team92.common.enums.ProjectStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateProjectCommand {
    private UUID id;

    @NotNull
    @NotEmpty(message = "please provide name project")
    private String name;

    private String description;
    private String domain;

    @NotNull
    private Boolean isPublic;

    private ProjectStatus projectStatus;
}
