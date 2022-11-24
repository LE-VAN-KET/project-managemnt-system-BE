package com.dut.team92.issuesservice.domain.dto;

import com.dut.team92.common.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class IssuesTypeDto {
    private String name;
    private String descriptions;
    private UUID organizationId;

    private Color color;
    private String urlIcon;
}
