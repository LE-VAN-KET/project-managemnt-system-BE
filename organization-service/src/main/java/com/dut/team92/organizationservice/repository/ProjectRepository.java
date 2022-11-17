package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends IJpaRepository<Project, UUID> {
    List<Project> findAllByOrganizationId(UUID organizationId);
    boolean existsByDomain(String domain);

    Optional<Project> findByIdAndOrganizationId(UUID uuid, UUID organizationId);
}
