package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SprintRepository extends IJpaRepository<Sprint, UUID> {
}
