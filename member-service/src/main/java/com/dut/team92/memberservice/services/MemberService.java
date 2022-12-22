package com.dut.team92.memberservice.services;

import com.dut.team92.memberservice.domain.dto.MemberDto;
import com.dut.team92.memberservice.domain.dto.UserDto;
import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.domain.dto.response.ProjectResponse;

import java.util.UUID;
import java.util.List;

public interface MemberService {
    boolean existMemberInProject(UUID memberId, UUID projectId);
    MemberDto addMemberToProject(AddMemberToProjectRequest command);
    List<UserDto> searchMemberInOrganization(UUID organizationId, String keyword);
    List<MemberDto> searchMemberInProject(UUID projectId, String keyword);
    List<ProjectResponse> getAllProjectIdByUserIdAndOrganizationId(UUID organizationId);
    MemberDto getMemberById(UUID memberId);
    void removeMemberInTheProject(UUID memberId);
}
