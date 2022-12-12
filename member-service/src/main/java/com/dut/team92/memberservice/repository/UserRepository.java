package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.dto.UserIdWithEmail;
import com.dut.team92.memberservice.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends IJpaRepository<User, UUID> {

    @Query("select new com.dut.team92.memberservice.domain.dto.UserIdWithEmail(u.id, u.mailNotification) from " +
            "User u where u.username = :username or u.mailNotification = :username")
    Optional<UserIdWithEmail> findUserByUsername(@Param("username") String username);

    List<User> findAllByOrganizationId(UUID organizationId);

    @Query("select u from User u where u.organizationId = ?1 and (u.username like %?2% or u.mailNotification like %?2%)")
    List<User> searchAllByUsernameOrMailNotification(UUID organizationId,
                                                     String keyword);

}
