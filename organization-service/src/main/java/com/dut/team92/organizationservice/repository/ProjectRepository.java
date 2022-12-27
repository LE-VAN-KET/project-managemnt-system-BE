package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends IJpaRepository<Project, UUID> {
    List<Project> findAllByOrganizationId(UUID organizationId);
    boolean existsByDomain(String domain);
    boolean existsById(UUID projectId);
    boolean existsByIdAndOrganizationId(UUID projectId, UUID organizationId);
    Optional<Project> findByIdAndOrganizationId(UUID uuid, UUID organizationId);

    @Query("SELECT p.key FROM Project p WHERE p.id = :projectId")
    Optional<String> findProjectKeyByProjectId(@Param("projectId") UUID projectId);

    List<Project> findAllByIdIn(List<UUID> projectIdList);
    boolean existsByKey(String key);
}
