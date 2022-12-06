package com.dut.team92.issuesservice.domain.dto.request;

import com.dut.team92.common.enums.Priority;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class IssuesRequest {
    private UUID id;

    @NotNull
    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private UUID projectId;

    private UUID trackerId;
    private Calendar startDate;
    private Calendar dueDate;

    private BigDecimal estimatedHours;

    private Priority priority;

    private IssuesStatusDto issuesStatusDto;

    private UUID authorId;
    private Integer doneRatio;

    private UUID tagId;

    private UUID boardId;
    private Boolean isPublic;

    private String issuesKey;
}
