package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SprintDataMapper extends BaseMapper<Sprint, SprintDto> {
    @Override
    public Sprint convertToEntity(SprintDto dto, Object... args) {
        Sprint sprint = new Sprint();
        if (dto != null) {
            BeanUtils.copyProperties(dto, sprint);
        }
        return sprint;
    }

    @Override
    public SprintDto convertToDto(Sprint entity, Object... args) {
        SprintDto dto =new SprintDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
        }
        return dto;
    }

    public Sprint createSprintCommandToSprint(CreateSprintCommand command) {
        Sprint sprint = new Sprint();
        if (command != null) {
            BeanUtils.copyProperties(command, sprint);
        }
        return sprint;
    }

}
