package com.dut.team92.userservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAdminOrganizationCommand extends CreateUserCommand implements Serializable {
    private String name;
    private String description;
    private String logo;
}
