package com.dut.team92.memberservice.services;

import com.dut.team92.memberservice.domain.dto.request.AddMemberToProjectRequest;
import com.dut.team92.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public boolean existMemberInProject(UUID memberId, UUID projectId) {
        return memberRepository.existsByIdAndProjectId(memberId, projectId);
    }

    @Override
    public void addMemberToProject(AddMemberToProjectRequest command) {

    }

    private void validateAddMemberToProjectCommand(AddMemberToProjectRequest command) {
    }
}
