package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.IssuesStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssuesStatusRepository extends IJpaRepository<IssuesStatus, UUID> {
    @Query("SELECT issuesStatus FROM IssuesStatus issuesStatus WHERE issuesStatus.organizationId IS NULL " +
            "OR issuesStatus.organizationId = :organizationId")
    List<IssuesStatus> findAllByOrganizationIdOrSystem(@Param("organizationId") UUID organizationId);

    @Query("SELECT issuesStatus FROM IssuesStatus issuesStatus WHERE (issuesStatus.organizationId IS NULL " +
            "OR issuesStatus.organizationId = :organizationId) AND UPPER(issuesStatus.name) = UPPER(:name)")
    Optional<IssuesStatus> findByNameAndOrganizationIdOrSystem(@Param("organizationId") UUID organizationId,
                                                               @Param("name") String name);
    @Query("SELECT issuesStatus FROM IssuesStatus issuesStatus WHERE UPPER(issuesStatus.name) = UPPER(:name)")
    Optional<IssuesStatus> findByName(@Param("name") String name);
}
