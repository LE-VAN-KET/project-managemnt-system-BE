package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.dto.response.Permission.PermissionResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse;
import com.dut.team92.memberservice.domain.dto.response.Permission.ScreenResponse;
import com.dut.team92.memberservice.domain.entity.Permissions;
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

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.RolesResponse(role.id, role.name, role.type) FROM Roles AS role WHERE role.organizationId = :organizationId")
    List<RolesResponse> getListRoles(@Param("organizationId") UUID organizationId);

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.PermissionResponse(p.id, p.functionId, p.enable, f.screenId, f.name, f.isRequired, f.isHidden, f.dependonFunction, f.code)" +
            " FROM Permissions p INNER JOIN Function f ON p.functionId = f.id WHERE p.role.id = :roleId")
    List<PermissionResponse> getListPermission(@Param("roleId") Long roleId);

    @Query("SELECT NEW com.dut.team92.memberservice.domain.dto.response.Permission.ScreenResponse(s.id, s.groupId,s.name,s.code,s.description) FROM Screens AS s")
    List<ScreenResponse> getListScreen();
}
