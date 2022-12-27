package com.dut.team92.organizationservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SprintRepository extends IJpaRepository<Sprint, UUID> {
    @Query("SELECT coalesce(MAX(cast(trim(substring(sp.name, locate(' ', sp.name) + 7)) as int)), 0) FROM " +
            "Sprint sp where sp.projectId = :projectId")
    int maxPositionByProjectId(@Param("projectId") UUID projectId);
    @Query("SELECT coalesce(MAX(position), 0) FROM Sprint")
    int maxPosition();
}
