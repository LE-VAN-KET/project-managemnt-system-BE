package com.dut.team92.memberservice.messages.listener.services.impl;

import com.dut.team92.common.enums.RoleType;
import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.Roles;
import com.dut.team92.memberservice.exception.RoleNotFoundException;
import com.dut.team92.memberservice.messages.listener.services.MemberRequestMessageListener;
import com.dut.team92.memberservice.messages.mapper.MemberMessageMapper;
import com.dut.team92.memberservice.repository.MemberRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRequestMessageListenerImpl implements MemberRequestMessageListener {
    private final MemberRepository memberRepository;
    private final RolesRepository rolesRepository;
    private final MemberMessageMapper memberMessageMapper;

    @Override
    public void completeAddMemberToOrganization(List<MemberRequestModel> memberRequestModelList) {
        Roles role = rolesRepository.findByName(RoleType.MEMBER.name()).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + RoleType.MEMBER.name()));

        List<Members> members = memberMessageMapper.memberRequestModelToMembers(memberRequestModelList, role);
        memberRepository.saveAll(members);
    }
}
