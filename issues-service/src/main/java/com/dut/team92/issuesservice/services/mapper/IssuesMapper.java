package com.dut.team92.issuesservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.request.CreateIssuesBacklogCommand;
import com.dut.team92.issuesservice.domain.entity.Issues;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssuesMapper extends BaseMapper<Issues, IssuesDto> {
    private final IssuesStatusMapper issuesStatusMapper;
    private final IssuesTypeMapper issuesTypeMapper;

    @Override
    public Issues convertToEntity(IssuesDto dto, Object... args) {
        Issues issues = new Issues();
        if (dto != null) {
            BeanUtils.copyProperties(dto, issues);
        }
        return issues;
    }

    @Override
    public IssuesDto convertToDto(Issues entity, Object... args) {
        IssuesDto issuesDto = new IssuesDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, issuesDto);
            if (entity.getChildren() != null && !entity.getChildren().isEmpty()) {
                issuesDto.setChildren(convertToDtoList(entity.getChildren()));
            }

            if(entity.getIssuesStatus() != null) {
                issuesDto.setIssuesStatusDto(issuesStatusMapper.convertToDto(entity.getIssuesStatus()));
            }

            if(entity.getIssuesType() != null) {
                issuesDto.setIssuesTypeDto(issuesTypeMapper.convertToDto(entity.getIssuesType()));
            }
        }
        return issuesDto;
    }

    public Issues createIssuesBacklogCommandToIssues(CreateIssuesBacklogCommand command) {
        Issues issues = new Issues();
        if (command != null) {
            BeanUtils.copyProperties(command, issues);
        }
        return issues;
    }
}
