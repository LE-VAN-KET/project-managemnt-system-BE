package com.dut.team92.memberservice.messages.listener.services.impl;

import com.dut.team92.common.enums.RoleType;
import com.dut.team92.kafka.model.MemberRequestModel;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.Roles;
import com.dut.team92.memberservice.domain.entity.User;
import com.dut.team92.memberservice.exception.RoleNotFoundException;
import com.dut.team92.memberservice.messages.listener.services.MemberRequestMessageListener;
import com.dut.team92.memberservice.messages.mapper.MemberMessageMapper;
import com.dut.team92.memberservice.messages.mapper.UserMessageMapper;
import com.dut.team92.memberservice.repository.MemberRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import com.dut.team92.memberservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberRequestMessageListenerImpl implements MemberRequestMessageListener {
    private final MemberRepository memberRepository;
    private final RolesRepository rolesRepository;
    private final MemberMessageMapper memberMessageMapper;
    private final UserMessageMapper userMessageMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void completeAddUserToOrganization(List<MemberRequestModel> memberRequestModelList) {
        List<User> userList = userMessageMapper.memberRequestModelToUser(memberRequestModelList);
        if (userList.size() == 1 && userList.get(0).getIsOrganizerAdmin()) {
            saveUserWithRole(userList.get(0), RoleType.ADMIN_ORGANIZATION.name());
        } else if (userList.get(0).getIsSystemAdmin()) {
            saveUserWithRole(userList.get(0), RoleType.SUPER_ADMIN.name());
        } else {
            saveAllUserWithDefaultRoleMember(memberRequestModelList);
        }
    }

    private void saveUserWithRole(User user, String roleName) {
        User savedUser = userRepository.save(user);
        Roles role = rolesRepository.findByName(roleName).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + roleName));
        Members members = memberMessageMapper.userToMembers(savedUser, role);
        memberRepository.save(members);
    }

    private void saveAllUserWithDefaultRoleMember(List<MemberRequestModel> memberRequestModelList) {
        List<User> userList = userMessageMapper.memberRequestModelToUser(memberRequestModelList);
        List<User> savedUserList = userRepository.saveAll(userList);
        Roles role = rolesRepository.findByName(RoleType.MEMBER.name()).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + RoleType.MEMBER.name()));
        List<Members> membersList = savedUserList.stream().map(u ->
                memberMessageMapper.userToMembers(u, role)).collect(Collectors.toList());
        memberRepository.saveAll(membersList);
    }
}
