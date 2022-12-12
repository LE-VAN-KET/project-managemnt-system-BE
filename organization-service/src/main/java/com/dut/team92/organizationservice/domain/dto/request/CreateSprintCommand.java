package com.dut.team92.organizationservice.domain.dto.request;

import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSprintCommand {
    @NotNull
    private UUID projectId;
    @NotNull
    @NotEmpty
    private String name;
    private String description;

//    @NotNull
//    @Min(1)
//    private int position;

    @NotNull
    private SprintStatus status;
}
