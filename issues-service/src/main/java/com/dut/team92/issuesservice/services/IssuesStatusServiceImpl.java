package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.entity.IssuesStatus;
import com.dut.team92.issuesservice.exception.IssuesStatusNotFoundException;
import com.dut.team92.issuesservice.repository.IssuesStatusRepository;
import com.dut.team92.issuesservice.services.mapper.IssuesStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "issuesStatusCache")
public class IssuesStatusServiceImpl implements IssuesStatusService{
    private final IssuesStatusRepository issuesStatusRepository;
    private final IssuesStatusMapper issuesStatusMapper;

    @Cacheable(cacheNames = "issues-type", key = "#organizationId")
    @Override
    @Transactional(readOnly = true)
    public List<IssuesStatusDto> getAllIssuesStatusByOrganizationId(UUID organizationId) {
        List<IssuesStatus> issuesStatuses = issuesStatusRepository.findAllByOrganizationIdOrSystem(organizationId);
        return issuesStatuses.isEmpty() ? Collections.emptyList()
                : issuesStatusMapper.convertToDtoList(issuesStatuses);
    }

    @Cacheable(cacheNames = "single-issues-type", key = "#organizationId-#name")
    @Override
    public IssuesStatusDto getOneIssuesStatusByNameAndOrganizationId(String name, UUID organizationId) {
        IssuesStatus issuesStatus = issuesStatusRepository.findByNameAndOrganizationIdOrSystem(organizationId, name)
                .orElseThrow(() -> new IssuesStatusNotFoundException("Issues status not found by name " + name));
        return issuesStatusMapper.convertToDto(issuesStatus);
    }
}
