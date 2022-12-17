package com.dut.team92.issuesservice.repository;

import com.dut.team92.common.enums.IssuesAssignStatus;
import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.issuesservice.domain.entity.Issues;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssuesRepository extends IJpaRepository<Issues, UUID>, HibernateRepository<Issues>, IssuesHibernate {

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss_status, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position, ia.memberId)  from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id left join IssuesAssign ia on iss.id = ia.issuesId " +
            "WHERE (ia is NULL or ia.status = :issuesAssignStatus) and iss.boardId IS NULL AND iss.projectId = :projectId")
    List<Issues> findAllByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId,
                                                    @Param("issuesAssignStatus")IssuesAssignStatus issuesAssignStatus);
    @Query("select coalesce(max(iss.position), 0) From Issues iss where iss.projectId = :projectId AND iss.boardId = :boardId")
    int maxPositionByProjectIdAndBoardId(@Param("projectId") UUID projectId,
                                          @Param("boardId") UUID boardId);
    @Query("select coalesce(max(iss.position), 0) From Issues iss where iss.projectId = :projectId and iss.boardId is null")
    int maxPositionByProjectIdAndBoardIdIsNull(@Param("projectId") UUID projectId);

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss_status, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position, ia.memberId) from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id left join IssuesAssign ia on iss.id = ia.issuesId " +
            "where (ia is NULL or ia.status = :issuesAssignStatus) and iss.boardId in (:boardIds)")
    List<Issues> findAllByBoardIdIn(@Param("boardIds") List<UUID> boardIds,
                                    @Param("issuesAssignStatus")IssuesAssignStatus issuesAssignStatus);

    @Query("select coalesce(max(cast(trim(substring(iss.issuesKey, locate('-', iss.issuesKey) + 1)) as int) ), 0) " +
            "from Issues iss where iss.projectId = :projectId")
    int maxIssuesKeyByProjectId(UUID projectId);

    @Query("SELECT new Issues(iss.id, iss.name, iss.issuesKey, iss.projectId, iss.priority, iss.issuesStatus, " +
            "iss.authorId, iss.boardId, iss.isPublic, iss_type, iss.position, ia.memberId) from Issues iss inner join " +
            "IssuesType iss_type on iss.issuesType.id = iss_type.id inner join IssuesStatus as iss_status " +
            "on iss.issuesStatus.id = iss_status.id left join IssuesAssign ia on iss.id = ia.issuesId " +
            "where (ia is NULL or ia.status = :issuesAssignStatus) and iss.projectId = :projectId")
    List<Issues> findAllByProjectId(@Param("projectId") UUID projectId,
                                    @Param("issuesAssignStatus")IssuesAssignStatus issuesAssignStatus);

    @Modifying
    @Query("update Issues iss set iss.boardId = :newBoardId where iss.boardId = :oldBoardId")
    void updateIssuesToOtherSprint(@Param("newBoardId") UUID newBoardId, @Param("oldBoardId") UUID oldBoardId);

    @Modifying
    @Query("update Issues iss set iss.boardId = NULL where iss.boardId in (:boardIdList)")
    void updateIssuesToBacklog(@Param("boardIdList") List<UUID> boardIdList);
}
