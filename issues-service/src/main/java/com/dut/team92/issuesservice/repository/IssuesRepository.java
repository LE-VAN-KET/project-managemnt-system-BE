package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.Issues;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuesRepository extends IJpaRepository<Issues, UUID> {
    @Query("SELECT iss FROM Issues  iss WHERE iss.boardId IS NULL AND iss.projectId = :projectId")
    List<Issues> findAllByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);
    long countByProjectId(UUID projectId);
}
