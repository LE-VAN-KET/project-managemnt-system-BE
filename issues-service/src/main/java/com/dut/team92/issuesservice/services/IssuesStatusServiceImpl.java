package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.entity.IssuesStatus;
import com.dut.team92.issuesservice.repository.IssuesStatusRepository;
import com.dut.team92.issuesservice.services.mapper.IssuesStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssuesStatusServiceImpl implements IssuesStatusService{
    private final IssuesStatusRepository issuesStatusRepository;
    private final IssuesStatusMapper issuesStatusMapper;

    @Override
    @Transactional(readOnly = true)
    public List<IssuesStatusDto> getAllIssuesStatusByOrganizationId(UUID organizationId) {
        List<IssuesStatus> issuesStatuses = issuesStatusRepository.findAllByOrganizationIdOrSystem(organizationId);
        return issuesStatuses.isEmpty() ? Collections.emptyList()
                : issuesStatusMapper.convertToDtoList(issuesStatuses);
    }
}
