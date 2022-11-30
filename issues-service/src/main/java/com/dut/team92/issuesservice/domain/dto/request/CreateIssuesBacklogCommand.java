package com.dut.team92.issuesservice.domain.dto.request;

import com.dut.team92.common.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateIssuesBacklogCommand {
    @NotNull
    @Min(1)
    private Long issueTypeId;

    @NotNull
    @NotEmpty
    private String name;
    private String description;

    @NotNull
    private UUID projectId;
    private Calendar startDate;
    private Calendar dueDate;
    private BigDecimal estimatedHours;
    private Priority priority;

    @NotNull
    private UUID issuesStatusId;
    private Integer doneRatio;
    private Boolean isPublic;
    private UUID parentId;

    private UUID assignMemberId;

    @NotNull
    private UUID organizationId;
    private UUID boardId;
}
