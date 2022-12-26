package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.dto.response.Permission.PermissionResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.ScreenResponse;
import com.dut.team92.memberservice.domain.entity.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolesRepository extends IJpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
    @Query(value = "select DISTINCT r.name from roles r inner join members_roles mr ON r.role_id  = mr.role_id " +
            "inner join members m on m.member_id = mr.member_id inner join `user` u " +
            "on u.user_id = m.user_id where u.user_id = :userId ", nativeQuery = true)
    List<String> getRolesByUserId(@Param("userId") UUID userId);

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse(role.id, role.name, role.type) FROM Roles AS role WHERE role.organizationId IS NULL OR (:organizationId IS NULL OR role.organizationId = :organizationId)")
    List<RolesResponse> getListRoles(@Param("organizationId") UUID organizationId);

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.PermissionResponse(p.id, p.functionId, p.enable, fos.screenId, f.name, f.isRequired, f.isHidden, f.dependonFunction, f.code)" +
            " FROM Permissions p INNER JOIN Function f ON p.functionId = f.id INNER JOIN FunctionOfScreen fos ON f.id = fos.functionId WHERE p.role.id = :roleId")
    List<PermissionResponse> getListPermission(@Param("roleId") Long roleId);

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.ScreenResponse(s.id, s.groupId,s.name,s.code,s.description) FROM Screens AS s")
    List<ScreenResponse> getListScreen();

    @Query("SELECT r.id FROM Roles r where  r.name = :name AND r.organizationId = :organizerId")
    Optional<Long> checkExist(@Param("name") String name,@Param("organizerId") UUID organizerId);
    @Query(value="CALL CheckPermissions(:userId, :projectId, :functionId)", nativeQuery = true)
    int checkPermission(@Param("functionId") UUID functionId, @Param("userId") UUID userId, @Param("projectId") UUID projectId);
}
