package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;

import java.util.List;
import java.util.UUID;

public interface SprintService {
    SprintDto createSprint(CreateSprintCommand command);
    SprintDto updateSprint(SprintDto dto);
    void completedSprint(UUID sprintId, MoveIssuesType moveIssuesType);
    List<SprintDto> getAllSprintByListStatus(UUID projectId, SprintStatus... statuses);
}
