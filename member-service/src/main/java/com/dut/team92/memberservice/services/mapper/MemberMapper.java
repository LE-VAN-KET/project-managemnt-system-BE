package com.dut.team92.memberservice.services.mapper;

import com.dut.team92.memberservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.memberservice.domain.entity.Members;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberMapper {

    public List<Members> createMembersDtoToMembers(List<CreateMemberDto> createMemberDtoList) {
        return null;
    }
}
