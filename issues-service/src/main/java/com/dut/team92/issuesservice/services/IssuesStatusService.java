package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;

import java.util.List;
import java.util.UUID;

public interface IssuesStatusService {
    List<IssuesStatusDto> getAllIssuesStatusByOrganizationId(UUID organizationId);
    IssuesStatusDto getOneIssuesStatusByNameAndOrganizationId(String name, UUID organizationId);
}
