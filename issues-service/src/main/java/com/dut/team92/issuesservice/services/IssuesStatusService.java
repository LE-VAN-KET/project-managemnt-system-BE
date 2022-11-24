package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;

import java.util.List;
import java.util.UUID;

public interface IssuesStatusService {
    List<IssuesStatusDto> getAllIssuesStatusByOrganizationId(UUID organizationId);
}
