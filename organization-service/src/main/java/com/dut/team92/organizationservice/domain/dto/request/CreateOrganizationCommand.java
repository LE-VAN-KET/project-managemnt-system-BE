package com.dut.team92.organizationservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateOrganizationCommand implements Serializable {
    @NotNull
    @NotEmpty
    private String name;

    private String description;

    private String logo;

//    @NotNull
    private UUID userId;
}
