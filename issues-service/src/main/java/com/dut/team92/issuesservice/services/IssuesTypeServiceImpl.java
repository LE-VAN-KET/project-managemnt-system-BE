package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;
import com.dut.team92.issuesservice.domain.entity.IssuesType;
import com.dut.team92.issuesservice.exception.IssuesTypeNotFoundException;
import com.dut.team92.issuesservice.repository.IssuesTypeRepository;
import com.dut.team92.issuesservice.services.mapper.IssuesTypeMapper;
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
@CacheConfig(cacheNames = "issuesTypeCache")
public class IssuesTypeServiceImpl implements IssuesTypeService {

    private final IssuesTypeRepository issuesTypeRepository;
    private final IssuesTypeMapper issuesTypeMapper;

    @Override
    public List<IssuesType> getAll() {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "issues_type", key = "#id")
    public IssuesType get(Long id) {
        return issuesTypeRepository.findById(id).orElseThrow(() ->
                new IssuesTypeNotFoundException("Issues not found with id equal " + id));
    }

    @Override
    public IssuesType create(IssuesType entity) {
        return null;
    }

    @Override
    public IssuesType update(IssuesType entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "issues_type_organization", key = "#organizationId")
    public List<IssuesTypeDto> getAllIssuesTypeByOrganizationId(UUID organizationId) {
        List<IssuesType> issuesTypes = issuesTypeRepository.findAllByOrganizationIdOrSystem(organizationId);
        return issuesTypes.isEmpty() ? Collections.emptyList()
                : issuesTypeMapper.convertToDtoList(issuesTypes);
    }

    @Override
    @Cacheable(cacheNames = "single_issues_type", key = "#organizationId")
    public IssuesTypeDto getOneIssuesTypeByNameAndOrganizationId(String name, UUID organizationId) {
        IssuesType issuesType = issuesTypeRepository.findByOrganizationIdOrSystem(organizationId, name).orElseThrow(() ->
                new IssuesTypeNotFoundException("Issues Type not found with name = " + name));
        return issuesTypeMapper.convertToDto(issuesType);
    }
}
