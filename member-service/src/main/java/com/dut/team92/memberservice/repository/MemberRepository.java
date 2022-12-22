package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.dto.response.ProjectResponse;
import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends IJpaRepository<Members, UUID> {
    boolean existsByIdAndProjectId(UUID memberId, UUID projectId);
    @Query("select new com.dut.team92.memberservice.domain.entity.Members(m.id, m.projectId, m.mailNotification, " +
            "u.firstName, u.lastName, u.displayName, u.id, u.username) from Members  m inner join User u " +
            "ON m.user.id = u.id where m.projectId = :projectId")
    List<Members> findAllByProjectId(@Param("projectId") UUID projectId);
    boolean existsByProjectIdAndUser(UUID projectId, User user);

    @Query("select new com.dut.team92.memberservice.domain.entity.Members(m.id, m.projectId, m.mailNotification, " +
            "u.firstName, u.lastName, u.displayName, u.id, u.username) from Members  m inner join " +
            "User u on m.user.id = u.id where m.projectId = ?1 and concat(NULLIF(u.username, '') , ' ', " +
            "nullif(u.mailNotification, '') , ' ', nullif(u.displayName, '') , ' ', nullif(u.firstName, '') " +
            ", ' ', nullif(u.lastName, '') ) like %?2%")
    List<Members> searchAllByProjectId(UUID projectId, String keyword);

    @Query("select new com.dut.team92.memberservice.domain.dto.response.ProjectResponse(m.projectId, m.user.id, " +
            "m.user.isOrganizerAdmin) from Members m inner join User u on m.user.id = u.id where " +
            "u.organizationId = :organizationId and m.user.id = :userId")
    List<ProjectResponse> getAllProjectIdByOrganizationIdAndUserId(@Param("organizationId") UUID organizationId,
                                                                   @Param("userId") UUID userId);

}
