package com.dut.team92.userservice.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class CreateUserAdminOrganizationResponse extends CreateUserResponse{
    private UUID organizationId;
    private String name;
    private String description;
    private Boolean isDelete;
    private String logo;

    public CreateUserAdminOrganizationResponse(@NotNull UUID userId, @NotNull String message) {
        super(userId, message);
    }
    public CreateUserAdminOrganizationResponse() {
        super(null, null);
    }
}
