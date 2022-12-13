package com.dut.team92.organizationservice.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    @NotNull
    private UUID id;

    @NotNull
    @NotEmpty
    private String name;
    private String description;
    @Min(1)
    private int position;
    @NotNull
    private UUID sprintId;
}
