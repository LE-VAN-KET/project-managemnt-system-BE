package com.dut.team92.organizationservice.domain.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateProjectCommand {
    @NotNull
    @NotEmpty
    private String name;

    private String description;
    private String domain;

    @NotNull
    @NotEmpty
    private String key;

    @NotNull
    private Boolean isPublic;
    private UUID parentId = null;
}
