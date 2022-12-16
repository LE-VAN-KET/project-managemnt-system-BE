package com.dut.team92.memberservice.services;

import com.dut.team92.common.enums.RoleType;
import com.dut.team92.common.security.model.CustomUserPrincipal;
import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.UserDto;
import com.dut.team92.memberservice.domain.dto.UserIdWithEmail;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.response.ProjectResponse;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.MembersRoles;
import com.dut.team92.memberservice.domain.entity.Roles;
import com.dut.team92.memberservice.domain.entity.User;
import com.dut.team92.memberservice.exception.MemberAlreadyExistInProject;
import com.dut.team92.memberservice.exception.RoleNotFoundException;
import com.dut.team92.memberservice.exception.UserNotFoundException;
import com.dut.team92.memberservice.repository.MemberRepository;
import com.dut.team92.memberservice.repository.RolesRepository;
import com.dut.team92.memberservice.repository.UserRepository;
import com.dut.team92.memberservice.services.mapper.MemberMapper;
import com.dut.team92.memberservice.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolesRepository rolesRepository;

    @Override
    public boolean existMemberInProject(UUID memberId, UUID projectId) {
        return memberRepository.existsByIdAndProjectId(memberId, projectId);
    }

    @Override
    @Transactional
    public MemberDto addMemberToProject(AddMemberToProjectRequest command) {
        UserIdWithEmail userIdWithEmail = userRepository.findUserByUsername(command.getUsernames()).orElseThrow(() ->
                new UserNotFoundException("Member not found with username: " + command.getUsernames()));
        User user = new User();
        user.setId(userIdWithEmail.getId());
        user.setMailNotification(userIdWithEmail.getMailNotification());
        boolean existMemberInProject = memberRepository.existsByProjectIdAndUser(command.getProjectId(), user);
        if (existMemberInProject) {
            throw new MemberAlreadyExistInProject("Member already exists in project!");
        }
        Members member = create(user, command.getProjectId());
        return memberMapper.convertToDto(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchMemberInOrganization(UUID organizationId, String keyword) {
        List<User> userList;
        if (Objects.isNull(keyword)) {
            userList = userRepository.findAllByOrganizationId(organizationId);
        } else {
            userList = userRepository.searchAllByUsernameOrMailNotification(organizationId, keyword);
        }
        return userList.isEmpty() ? Collections.emptyList(): userMapper.convertToDtoList(userList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDto> searchMemberInProject(UUID projectId, String keyword) {
        List<Members> membersList;
        if (Objects.isNull(keyword)) {
            membersList = memberRepository.findAllByProjectId(projectId);
        } else {
            membersList = memberRepository.searchAllByProjectId(projectId, keyword);
        }
        return membersList.isEmpty() ? Collections.emptyList(): memberMapper.convertToDtoList(membersList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjectIdByUserIdAndOrganizationId(UUID organizationId) {
        CustomUserPrincipal principal = (CustomUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String userId = principal.getSubId();
        boolean isOrganizationAdmin = userRepository.existsByIdAndIsOrganizerAdmin(UUID.fromString(userId), true);
        if (isOrganizationAdmin) {
            return Arrays.asList(new ProjectResponse(null, UUID.fromString(userId), true));
        }

        List<ProjectResponse> projectIdList = memberRepository.getAllProjectIdByOrganizationIdAndUserId(organizationId,
                UUID.fromString(userId));
        return projectIdList.isEmpty() ? Collections.emptyList(): projectIdList;
    }

    private Members create(User user, UUID projectId) {
        Members members = new Members();
        members.setUser(user);
        members.setMailNotification(user.getMailNotification());
        members.setProjectId(projectId);

        Roles roles = getRoleByName(RoleType.DEVELOPER.name());

        MembersRoles membersRoles = new MembersRoles();
        membersRoles.setRole(roles);
        membersRoles.setMember(members);
        members.setMembersRoles(membersRoles);
        return memberRepository.save(members);
    }

    private Roles getRoleByName(String name) {
        return rolesRepository.findByName(name).orElseThrow(() ->
                new RoleNotFoundException("Role not found with name " + name));
    }
}
