package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.Board;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.domain.entity.SprintWithBoard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends IJpaRepository<Board, UUID> {
    List<Board> findALlBySprintId(UUID sprintId);

    @Query("select b as board, s as sprint From Board b INNER JOIN Sprint s ON b.sprintId = s.id " +
            "WHERE s.projectId = :projectId and s.status IN (:sprintStatus)")
    List<SprintWithBoard> findAllBySprintStatusInAndProjectId(@Param(("sprintStatus")) List<SprintStatus> status,
                                                        @Param("projectId") UUID projectId);
}
