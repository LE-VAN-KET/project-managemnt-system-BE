package com.dut.team92.memberservice.services;

import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;

import java.util.UUID;

public interface MemberService {
    boolean existMemberInProject(UUID memberId, UUID projectId);
    void addMemberToProject(AddMemberToProjectRequest command);
}
