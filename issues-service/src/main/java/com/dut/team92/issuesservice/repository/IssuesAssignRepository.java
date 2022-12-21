package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.enums.IssuesAssignStatus;
import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.IssuesAssign;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssuesAssignRepository extends IJpaRepository<IssuesAssign, UUID> {
    Optional<IssuesAssign> findByIssuesIdAndStatus(UUID issuesId, IssuesAssignStatus status);
}
