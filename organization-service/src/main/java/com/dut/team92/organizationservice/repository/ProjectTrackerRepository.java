package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.ProjectTracker;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectTrackerRepository extends IJpaRepository<ProjectTracker, UUID> {
}
