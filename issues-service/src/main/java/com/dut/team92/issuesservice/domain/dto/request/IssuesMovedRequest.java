package com.dut.team92.issuesservice.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class IssuesMovedRequest {
    @NotNull
    private UUID id;

    private String name;

    private UUID boardId;

    @Min(0)
    private int position;

}
