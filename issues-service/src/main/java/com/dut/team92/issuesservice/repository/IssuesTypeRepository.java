package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.IssuesType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuesTypeRepository extends IJpaRepository<IssuesType, Long> {
    @Query("SELECT issuesType FROM IssuesType issuesType WHERE issuesType.organizationId IS NULL " +
            "OR issuesType.organizationId = :organizationId")
    List<IssuesType> findAllByOrganizationIdOrSystem(@Param("organizationId") UUID organizationId);
}
