package com.dut.team92.memberservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class AddMemberToProjectRequest {
    @NotNull
    private UUID projectId;

    @NotNull
    private String usernames;
}
