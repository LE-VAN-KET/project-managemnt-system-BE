package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;

public interface SprintService {
    SprintDto createSprint(CreateSprintCommand command);
}
