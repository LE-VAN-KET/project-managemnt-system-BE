package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.Issues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuesRepository extends IJpaRepository<Issues, UUID> {
    @Query("SELECT iss FROM Issues  iss WHERE iss.boardId IS NULL AND iss.projectId = :projectId")
    List<Issues> findAllByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);
    @Query("select coalesce(max(iss.id), 0) From Issues iss where iss.projectId = :projectId AND iss.boardId = :boardId")
    int maxPositionByProjectIdAndBoardId(@Param("projectId") UUID projectId,
                                          @Param("boardId") UUID boardId);
    @Query("select coalesce(max(iss.id), 0) From Issues iss where iss.projectId = :projectId and iss.boardId is null")
    int maxPositionByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);

    List<Issues> findAllByBoardIdIn(List<UUID> boardIds);
    int countByProjectId(UUID projectId);
}
