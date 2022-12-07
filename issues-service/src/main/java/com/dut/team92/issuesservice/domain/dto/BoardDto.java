package com.dut.team92.issuesservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto {
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    private String description;
    private int position;
    private UUID sprintId;
    private List<IssuesDto> issuesDtoList;
}
