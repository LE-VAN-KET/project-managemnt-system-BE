package com.dut.team92.issuesservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.issuesservice.domain.dto.IssuesStatusDto;
import com.dut.team92.issuesservice.domain.entity.IssuesStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class IssuesStatusMapper extends BaseMapper<IssuesStatus, IssuesStatusDto> {
    @Override
    public IssuesStatus convertToEntity(IssuesStatusDto dto, Object... args) {
        IssuesStatus issuesStatus = new IssuesStatus();
        if (dto != null) {
            BeanUtils.copyProperties(dto, issuesStatus);
        }
        return issuesStatus;
    }

    @Override
    public IssuesStatusDto convertToDto(IssuesStatus entity, Object... args) {
        IssuesStatusDto issuesStatusDto = new IssuesStatusDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, issuesStatusDto);
        }
        return issuesStatusDto;
    }
}
