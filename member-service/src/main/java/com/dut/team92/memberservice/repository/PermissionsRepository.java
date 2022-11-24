package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.entity.Permissions;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionsRepository extends IJpaRepository<Permissions, UUID> {
}
