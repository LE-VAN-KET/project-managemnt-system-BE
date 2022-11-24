package com.dut.team92.memberservice.messages.mapper;

import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.MembersRoles;
import com.dut.team92.memberservice.domain.entity.Roles;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MemberMessageMapper {

    public List<Members> memberRequestModelToMembers(List<MemberRequestModel> memberRequestModelList,
                                                     Roles role) {
        return memberRequestModelList.stream().filter(Objects::nonNull).map(m -> {
            Members members = new Members();
            BeanUtils.copyProperties(m, members);
            MembersRoles membersRoles = new MembersRoles();
            membersRoles.setRole(role);
            membersRoles.setMember(members);
            members.setMembersRoles(membersRoles);
            return members;
        }).collect(Collectors.toList());
    }
}
