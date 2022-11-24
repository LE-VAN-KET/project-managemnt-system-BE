package com.dut.team92.issuesservice.domain.dto;

import com.dut.team92.common.domain.converter.ColorConverter;
import com.dut.team92.common.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class IssuesStatusDto {
    private UUID id;

    private String name;
    private String description;
    private Color color;
    private UUID organizationId;
}
