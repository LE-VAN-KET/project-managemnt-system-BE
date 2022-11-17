package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.entity.MembersRoles;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRolesRepository extends IJpaRepository<MembersRoles, UUID> {
}
