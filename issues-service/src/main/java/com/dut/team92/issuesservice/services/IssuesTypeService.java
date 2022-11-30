package com.dut.team92.issuesservice.services;

import com.dut.team92.common.service.RepositoryService;
import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;
import com.dut.team92.issuesservice.domain.entity.IssuesType;

import java.util.List;
import java.util.UUID;

public interface IssuesTypeService extends RepositoryService<IssuesType> {
    List<IssuesTypeDto> getAllIssuesTypeByOrganizationId(UUID organizationId);
    IssuesTypeDto getOneIssuesTypeByNameAndOrganizationId(String name, UUID organizationId);
}
