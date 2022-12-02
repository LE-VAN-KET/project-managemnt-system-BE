package com.dut.team92.issuesservice.domain.dto;

import com.dut.team92.common.enums.Color;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuesStatusDto {
    private UUID id;

    private String name;
    private String description;
    private Color color;
    private UUID organizationId;
}
