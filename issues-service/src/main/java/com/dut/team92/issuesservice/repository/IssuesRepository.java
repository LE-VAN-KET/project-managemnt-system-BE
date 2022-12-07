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

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss_status, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position)  from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id  WHERE iss.boardId IS NULL " +
            "AND iss.projectId = :projectId")
    List<Issues> findAllByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);
    @Query("select coalesce(max(iss.position), 0) From Issues iss where iss.projectId = :projectId AND iss.boardId = :boardId")
    int maxPositionByProjectIdAndBoardId(@Param("projectId") UUID projectId,
                                          @Param("boardId") UUID boardId);
    @Query("select coalesce(max(iss.position), 0) From Issues iss where iss.projectId = :projectId and iss.boardId is null")
    int maxPositionByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss_status, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position) from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id where iss.boardId in (:boardIds)")
    List<Issues> findAllByBoardIdIn(@Param("boardIds") List<UUID> boardIds);
    int countByProjectId(UUID projectId);

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss.issuesStatus, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position) from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id where iss.projectId = ?1")
    List<Issues> findAllByProjectId(UUID projectId);
}
