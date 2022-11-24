package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.entity.Members;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends IJpaRepository<Members, UUID> {
    boolean existsByIdAndProjectId(UUID memberId, UUID projectId);
}
