package com.dut.team92.issuesservice.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BacklogDto {
    private List<IssuesDto> backlog;
    private List<SprintDto> sprints;
}
