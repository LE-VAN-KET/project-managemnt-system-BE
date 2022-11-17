package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.Issues;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IssuesRepository extends IJpaRepository<Issues, UUID> {
}
