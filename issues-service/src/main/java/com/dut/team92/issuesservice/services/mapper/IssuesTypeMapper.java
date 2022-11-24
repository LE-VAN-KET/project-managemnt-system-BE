package com.dut.team92.issuesservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.IssuesTypeDto;
import com.dut.team92.issuesservice.domain.entity.IssuesType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class IssuesTypeMapper extends BaseMapper<IssuesType, IssuesTypeDto> {
    @Override
    public IssuesType convertToEntity(IssuesTypeDto dto, Object... args) {
        IssuesType issuesType = new IssuesType();
        if (dto != null) {
            BeanUtils.copyProperties(dto, issuesType);
        }
        return issuesType;
    }

    @Override
    public IssuesTypeDto convertToDto(IssuesType entity, Object... args) {
        IssuesTypeDto issuesTypeDto = new IssuesTypeDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, issuesTypeDto);
        }
        return issuesTypeDto;
    }
}
