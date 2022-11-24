package com.dut.team92.userservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.userservice.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends IJpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByMailNotification(String mailNotification);

    boolean existsByUsername(String username);

    boolean existsByMailNotification(String mailNotification);

    Optional<User> findByUsernameEqualsIgnoreCase(String username);
}
