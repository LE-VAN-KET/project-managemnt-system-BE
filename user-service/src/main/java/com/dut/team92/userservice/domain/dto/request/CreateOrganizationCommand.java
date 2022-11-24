package com.dut.team92.userservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreateOrganizationCommand implements Serializable {
    private String name;
    private String description;
    private String logo;
    private UUID userId;
}
