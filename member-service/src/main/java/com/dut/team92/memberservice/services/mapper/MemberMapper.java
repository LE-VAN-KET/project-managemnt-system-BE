package com.dut.team92.memberservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper extends BaseMapper<Members, MemberDto> {

    @Override
    public Members convertToEntity(MemberDto dto, Object... args) {
        return null;
    }

    @Override
    public MemberDto convertToDto(Members entity, Object... args) {
        MemberDto memberDto = new MemberDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, memberDto);
            if (entity.getDisplayName() != null) {
                memberDto.setDisplayName(entity.getDisplayName());
            } else if (entity.getFirstName() != null && entity.getLastName() != null) {
                memberDto.setDisplayName(StringUtils.join(entity.getFirstName(),
                        " ", entity.getLastName()));
            } else {
                memberDto.setDisplayName(entity.getUsername());
            }
        }
        return memberDto;
    }
}
