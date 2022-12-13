package com.dut.team92.organizationservice.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateBoardCommand {
    @NotNull
    @NotEmpty
    private String name;
    private String description;

    @NotNull
    @Min(1)
    private int position;
}
