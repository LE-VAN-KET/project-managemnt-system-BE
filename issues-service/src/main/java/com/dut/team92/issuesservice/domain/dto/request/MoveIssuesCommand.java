package com.dut.team92.issuesservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MoveIssuesCommand {
    @NotNull
    private UUID projectId;
    private List<IssuesMovedRequest> backlogs;
    private List<SprintRequest> sprints;
}
