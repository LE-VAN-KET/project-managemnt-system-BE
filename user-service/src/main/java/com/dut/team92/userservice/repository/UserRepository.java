package com.dut.team92.userservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.userservice.domain.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends IJpaRepository<User, UUID> {
}
