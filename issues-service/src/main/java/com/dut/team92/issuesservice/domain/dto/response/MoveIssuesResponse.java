package com.dut.team92.issuesservice.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MoveIssuesResponse {
    private int code;
    private String message;
}
