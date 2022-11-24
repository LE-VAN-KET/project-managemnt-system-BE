package com.dut.team92.organizationservice.domain.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateProjectCommand {
    @NonNull
    @NotEmpty
    private String name;

    private String description;
    private String domain;

    @NonNull
    private Boolean isPublic;
    private UUID parentId = null;
}
