package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
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
}
