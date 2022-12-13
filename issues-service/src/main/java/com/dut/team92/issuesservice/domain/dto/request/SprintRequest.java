package com.dut.team92.issuesservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SprintRequest {
    private UUID id;
    private List<IssuesMovedRequest> issuesList;
}
